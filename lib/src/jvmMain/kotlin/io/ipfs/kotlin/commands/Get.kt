package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ktor.client.statement.*
import io.ktor.utils.io.jvm.javaio.*
import java.io.InputStream

class Get(val ipfs: IPFSConnection) {

    /**
     * Cat IPFS content and return it as string.
     *
     * @param hash The hash of the content in base58.
     */
    suspend fun cat(hash: String): String = ipfs.callCmd("cat/$hash").bodyAsText()

    /**
     * Cat IPFS content and return it as ByteArray.
     *
     * @param hash The hash of the content in base58.
     */
    suspend fun catBytes(hash: String): ByteArray = ipfs.callCmd("cat/$hash").readBytes()

    /**
     * Cat IPFS content and process it using InputStream.
     *
     * @param hash The hash of the content in base58.
     * @param handler Callback which handle processing the input stream. When the callback return the stream and the request body will be closed.
     */
    suspend fun catStream(hash: String, handler: (stream: InputStream) -> Unit): Unit =
            ipfs.callCmd("cat/$hash").let { response ->
                val inputStream = response.bodyAsChannel().toInputStream()
                inputStream.use(handler)
            }

}