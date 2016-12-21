package io.ipfs.kotlin.commands

import com.squareup.moshi.JsonAdapter
import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.NamedHash
import okhttp3.*
import java.io.File
import java.net.URLEncoder

class Add(val ipfs: IPFSConnection) {

    private val adapter: JsonAdapter<NamedHash> by lazy { ipfs.moshi.adapter(NamedHash::class.java) }

    @JvmOverloads fun file(file: File, name: String = "file", filename: String = name): NamedHash {

        return addGeneric {
            addFile(it, file, name, filename)
        }

    }

    // this has to be outside the lambda because it is reentrant to handle subdirectory structures
    private fun addFile(builder: MultipartBody.Builder, file: File, name: String, filename: String) {

        if (file.isDirectory) {
            // add directory
            builder.addPart(Headers.of("Content-Disposition", "file; filename=\""
                    + URLEncoder.encode(filename, "UTF-8") + "\"", "Content-Transfer-Encoding", "binary"),
                    RequestBody.create(MediaType.parse("application/x-directory"), ""))
            // add files and subdirectories
            for (f: File in file.listFiles()) {
                addFile(builder, f, f.name, filename + "/" + f.name)
            }
        } else {
            builder.addPart(Headers.of("Content-Disposition", "file; filename=\""
                    + URLEncoder.encode(filename, "UTF-8") + "\"", "Content-Transfer-Encoding", "binary"),
                    RequestBody.create(MediaType.parse("application/octet-stream"), file))
        }

    }

    @JvmOverloads fun string(text: String, name: String = "string", filename: String = name): NamedHash {

        return addGeneric {
            val body = RequestBody.create(MediaType.parse("application/octet-stream"), text)
            it.addFormDataPart(name, filename, body)
        }

    }

    private fun addGeneric(withBuilder: (MultipartBody.Builder) -> Any): NamedHash {

        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        withBuilder(builder)
        val requestBody = builder.build();

        val request = Request.Builder()
                .url("${ipfs.base_url}add?stream-channels=true&progress=false")
                .post(requestBody)
                .build();

        val response = ipfs.okHttpClient.newCall(request).execute().body()
        var result: NamedHash = NamedHash("nameprobe","hashprobe")

//        val result = adapter.fromJson(response.source())
        System.out.println("addGeneric() response.charStream()")
        response.charStream().readLines().forEach {
            System.out.println(it)
        }
        System.out.println("addGeneric() result uninitialized")
        System.out.println(result)
        response.close()
        return result

    }
}
