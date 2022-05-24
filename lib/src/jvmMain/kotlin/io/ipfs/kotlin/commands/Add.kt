package io.ipfs.kotlin.commands

import com.squareup.moshi.JsonAdapter
import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.NamedHash
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.net.URLEncoder

class Add(val ipfs: IPFSConnection) {

    private val adapter: JsonAdapter<NamedHash> by lazy {
        ipfs.config.moshi.adapter(NamedHash::class.java)
    }

    // Accepts a single file or directory and returns the named hash.
    // For directories we return the hash of the enclosing
    // directory because that makes the most sense, also for
    // consistency with the java-ipfs-api implementation.
    fun file(file: File, name: String = "file", filename: String = name) = addGeneric {
        addFile(it, file, name, filename)
    }.last()


    // Accepts a single file or directory and returns the named hash.
    // Returns a collection of named hashes for the containing directory
    // and all sub-directories.
    fun directory(file: File, name: String = "file", filename: String = name) = addGeneric {
        addFile(it, file, name, filename)
    }


    // this has to be outside the lambda because it is reentrant to handle subdirectory structures
    private fun addFile(builder: MultipartBody.Builder, file: File, name: String, filename: String) {

        val encodedFileName = URLEncoder.encode(filename, "UTF-8")
        val headers = Headers.headersOf("Content-Disposition", "file; filename=\"$encodedFileName\"", "Content-Transfer-Encoding", "binary")
        if (file.isDirectory) {
            // add directory
            builder.addPart(headers, "".toRequestBody("application/x-directory".toMediaType()))
            // add files and subdirectories
            for (f: File in file.listFiles()) {
                addFile(builder, f, f.name, filename + "/" + f.name)
            }
        } else {
            builder.addPart(headers, file.asRequestBody("application/octet-stream".toMediaType()))
        }

    }

    fun string(text: String, name: String = "string", filename: String = name): NamedHash {

        return addGeneric {
            val body = RequestBody.create("application/octet-stream".toMediaType(), text)
            it.addFormDataPart(name, filename, body)
        }.last()
        // there can be only one

    }

    private fun addGeneric(withBuilder: (MultipartBody.Builder) -> Any): List<NamedHash> {

        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        withBuilder(builder)
        val requestBody = builder.build()

        val request = Request.Builder()
                .url("${ipfs.config.base_url}add?stream-channels=true&progress=false")
                .post(requestBody)
                .build()

        val response = ipfs.config.okHttpClient.newCall(request).execute().body

        response.use { responseBody ->
            return responseBody!!.charStream().readLines().asSequence().map {
                adapter.fromJson(it)
            }.filterNotNull().toList()
        }
    }
}
