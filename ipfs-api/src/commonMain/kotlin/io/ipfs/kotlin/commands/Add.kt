package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.NamedResponse
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okio.BufferedSource
import okio.Path

data class AddUploadProgress(val bytesSent: Long, val byteSize: Long)
data class AddProcessedProgress(var bytesProcessed: Long, val byteSize: Long)

typealias AddProgressCallback = ((AddUploadProgress?, AddProcessedProgress?) -> Unit)

class Add(val ipfs: IPFSConnection) {

    /*** Accepts a single file or directory and returns the named hash.
     * For directories, we return the hash of the enclosing
     * directory because that makes the most sense, also for
     * consistency with the java-ipfs-api implementation.
     **/
    suspend fun file(
        file: Path,
        name: String = "file",
        filename: String = name,
        progressCallback: AddProgressCallback? = null
    ) = addGeneric(progressCallback) {
        println(ipfs.config.fileSystem.openReadOnly(file).size())
        addFile(file, name, filename)
    }.last()

    /***
     * Accepts a single file's BufferedSource and returns the named hash.
     **/
    suspend fun file(
        source: BufferedSource,
        name: String = "file",
        filename: String = name,
        progressCallback: AddProgressCallback? = null
    ) = addGeneric(progressCallback) {
        val encodedFileName = filename.encodeURLParameter()
        val headersBuilder = HeadersBuilder()
        headersBuilder.append(HttpHeaders.ContentDisposition, "filename=\"$encodedFileName\"")
        headersBuilder.append("Content-Transfer-Encoding", "binary")
        headersBuilder.append(HttpHeaders.ContentType, ContentType.Application.OctetStream)
        append(name, source.readByteArray(), headersBuilder.build())
    }.last()


    /***
     * Accepts a single file or directory and returns the named hash.
     * Returns a collection of named hashes for the containing directory
     * and all sub-directories.
     */
    suspend fun directory(path: Path, name: String = "file", filename: String = name) = addGeneric(null) {
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

    suspend fun string(text: String, name: String = "string", filename: String = name): NamedResponse {

        return addGeneric(null) {
            append(name, text, Headers.build {
                append(HttpHeaders.ContentType, ContentType.Application.OctetStream)
                append(HttpHeaders.ContentDisposition, "filename=\"$filename\"")
            })
        }.last()
        // there can be only one

    }

    private suspend fun addGeneric(
        progressCallback: AddProgressCallback?,
        withBuilder: FormBuilder.() -> Unit
    ): List<NamedResponse> {
        val request = MultiPartFormDataContent(formData(withBuilder))
        val progress = progressCallback != null
        val result: List<NamedResponse> =
            ipfs.config.ktorClient.preparePost("${ipfs.config.base_url}add?progress=$progress") {
                onUpload { bytesSentTotal, contentLength ->
                    val uploadProgress = AddUploadProgress(bytesSentTotal, contentLength)
                    progressCallback?.invoke(uploadProgress, null)
                }
                setBody(request)
            }.execute { httpResponse ->
                // todo: figure out how to calculate the total size returned by ipfs before add completion. This isn't really correct to set byteSize with content length. Ipfs returns a slightly larger final number
                val processedProgress =
                    httpResponse.call.request.content.contentLength?.let { AddProcessedProgress(0, it) }

                val channel = httpResponse.bodyAsChannel()
                while (!channel.isClosedForRead) {
                    val progressNamedResponse: NamedResponse? =
                        channel.readUTF8Line()?.let { Json.decodeFromString(it) }
                    progressNamedResponse?.size
                    processedProgress?.bytesProcessed =
                        progressNamedResponse?.bytes ?: progressNamedResponse?.size?.toLong() ?: 0
                    progressCallback?.invoke(null, processedProgress)
                }

                return@execute try {
                    listOf(httpResponse.body())
                } catch (_: Throwable) {
                    httpResponse.body()
                }
            }
        return result
    }
}
