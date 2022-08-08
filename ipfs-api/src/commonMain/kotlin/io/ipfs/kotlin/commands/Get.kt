package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*

class Get(val ipfs: IPFSConnection) {

    /**
     * Cat IPFS content and return it as string.
     *
     * @param hash The hash of the content in base58.
     */
    suspend fun cat(hash: String): String = catBytes(hash).decodeToString()

    /**
     * Cat IPFS content and return it as ByteArray.
     *
     * @param hash The hash of the content in base58.
     */
    suspend fun catBytes(hash: String): ByteArray {
        val r = ipfs.callCmd("cat?arg=$hash")
        return r.readBytes()
    }

    /**
     * Cat IPFS content and process it using ByteReadChannel.
     *
     * @param hash The hash of the content in base58.
     * @param handler Callback which handle processing the input stream. When the callback return the stream and the request body will be closed.
     */
    suspend fun catReadChannel(hash: String, handler: (stream: ByteReadChannel) -> Unit): Unit =
        ipfs.callCmd("cat?arg=$hash").let { response ->
            val channel = response.bodyAsChannel()
            handler.invoke(channel)
        }

}