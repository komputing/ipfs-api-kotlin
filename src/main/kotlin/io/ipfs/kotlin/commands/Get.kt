package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import okhttp3.ResponseBody
import java.io.InputStream

class Get(val ipfs: IPFSConnection) {

    /**
     * Cat IPFS content and return it as string.
     *
     * @param hash The hash of the content in base58.
     */
    fun cat(hash: String): String = ipfs.callCmd("cat/$hash").use(ResponseBody::string)

    /**
     * Cat IPFS content and return it as ByteArray.
     *
     * @param hash The hash of the content in base58.
     */
    fun catBytes(hash: String): ByteArray = ipfs.callCmd("cat/$hash").use(ResponseBody::bytes)

    /**
     * Cat IPFS content and process it using InputStream.
     *
     * @param hash The hash of the content in base58.
     * @param handler Callback which handle processing the input stream. When the callback return the stream and the request body will be closed.
     */
    fun catStream(hash: String, handler: (stream: InputStream) -> Unit): Unit =
            ipfs.callCmd("cat/$hash").use { body ->
                val inputStream = body.byteStream()
                inputStream.use(handler)
            }

}