package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.NamedHash
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import okio.Path

class Add(val ipfs: IPFSConnection) {

    // Accepts a single file or directory and returns the named hash.
    // For directories we return the hash of the enclosing
    // directory because that makes the most sense, also for
    // consistency with the java-ipfs-api implementation.
    suspend fun file(file: Path, name: String = "file", filename: String = name) = addGeneric {
        addFile(file, name, filename)
    }.last()


    // Accepts a single file or directory and returns the named hash.
    // Returns a collection of named hashes for the containing directory
    // and all sub-directories.
    suspend fun directory(path: Path, name: String = "file", filename: String = name) = addGeneric {
        addFile(path, name, filename)
    }


    // this has to be outside the lambda because it is reentrant to handle subdirectory structures
    private fun FormBuilder.addFile(path: Path, name: String, filename: String) {

        val encodedFileName = filename.encodeURLParameter()

        val headersBuilder = HeadersBuilder()

        headersBuilder.append(HttpHeaders.ContentDisposition, "filename=\"$encodedFileName\"")
        headersBuilder.append("Content-Transfer-Encoding", "binary")
        val dirFiles = ipfs.config.fileSystem.listOrNull(path)
        val isDir = dirFiles?.isNotEmpty() ?: false
        if (isDir) {
            // add directory
            headersBuilder.append(HttpHeaders.ContentType, ContentType("application", "x-directory"))
            append("", "", headersBuilder.build())

            // add files and subdirectories
            for (p: Path in dirFiles!!) {
                addFile(p, p.name, filename + "/" + p.name)
            }
        } else {
            headersBuilder.append(HttpHeaders.ContentType, ContentType.Application.OctetStream)
            ipfs.config.fileSystem.read(path) {
                append(name, this.readByteArray(), headersBuilder.build())
            }
        }

    }

    suspend fun string(text: String, name: String = "string", filename: String = name): NamedHash {

        return addGeneric {
            append(name, text, Headers.build {
                append(HttpHeaders.ContentType, ContentType.Application.OctetStream)
                append(HttpHeaders.ContentDisposition, "filename=\"$filename\"")
            })
        }.last()
        // there can be only one

    }

    private suspend fun addGeneric(withBuilder: FormBuilder.() -> Unit): List<NamedHash> {
        val request = MultiPartFormDataContent(formData(withBuilder))
        val result = ipfs.config.ktorClient.post("${ipfs.config.base_url}add?stream-channels=true&progress=false"){
            setBody(request)
        }
        return try {
            listOf(result.body())
        } catch (_: Throwable){
            result.body()
        }
    }
}
