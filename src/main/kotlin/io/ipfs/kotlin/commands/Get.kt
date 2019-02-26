package io.ipfs.kotlin.commands

import com.le.ipfsapi.IPFSConnection
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.InputStream

class Get(val ipfs: IPFSConnection) {

    /**
     * Cat IPFS content and return it as string.
     *
     * @param hash The hash of the content in base58.
     */
    fun cat(hash: String): String? {
        val response = catApi(hash)
        return if (response.isSuccessful && null != response.body()) {
            response.body()!!.use(ResponseBody::string)
        } else {
            null
        }
    }

    /**
     * Cat IPFS content and return it as ByteArray.
     *
     * @param hash The hash of the content in base58.
     */
    fun catBytes(hash: String): ByteArray? {
        val response = catApi(hash)
        return if (response.isSuccessful && null != response.body()) {
            response.body()!!.use { it.bytes() }
        } else {
            null
        }
    }

    /**
     * Cat IPFS content and process it using InputStream.
     *
     * @param hash The hash of the content in base58.
     * @param handler Callback which handle processing the input stream. When the callback return the stream and the request body will be closed.
     */
    fun catStream(hash: String, handler: (stream: InputStream?) -> Unit): Unit {
        val response = catApi(hash)
        if (response.isSuccessful && null != response.body()) {
            response.body()!!.use { it.byteStream().use(handler) }
        } else {
            handler.invoke(null)
        }
    }

    /**
     * If you cat a wrong hash, the ipfs http api will response code 500
     * and with message. In this case if we use the response boy and cover it
     * to stream or byte ,it will not the true content of the hash code,it will
     * be the content of the 'Wrong Message'.
     *
     * So we need check the response code by {response.isSuccessful} to insure
     * we go the right response.
     */
    private fun catApi(hash: String):Response{
        val request = Request.Builder()
            .url(ipfs.config.base_url + "cat/$hash")
            .build()
        return ipfs.config.okHttpClient.newCall(request).execute()
    }

}