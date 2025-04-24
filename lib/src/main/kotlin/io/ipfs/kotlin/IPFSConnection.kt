package io.ipfs.kotlin

import com.squareup.moshi.JsonAdapter
import io.ipfs.kotlin.model.MessageWithCode
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody

open class IPFSConnection(val config: IPFSConfiguration) {

    var lastError: MessageWithCode? = null

    private val errorAdapter: JsonAdapter<MessageWithCode> by lazy {
        config.moshi.adapter(MessageWithCode::class.java)
    }

    fun callCmd(cmd: String): ResponseBody {
        val request = Request.Builder()
                .post(RequestBody.create(null, ""))
                .url(config.base_url + cmd)
                .build()

        return config.okHttpClient.newCall(request).execute().body()!!
    }

    fun executeCmd(cmd: String): Response {
        val request = Request.Builder()
            .post(RequestBody.create(null, ""))
            .url(config.base_url + cmd)
            .build()

        return config.okHttpClient.newCall(request).execute()
    }

    fun setErrorByJSON(jsonString: String) {
        lastError = errorAdapter.fromJson(jsonString)
    }
}