package io.ipfs.kotlin

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.ipfs.kotlin.model.MessageWithCode
import okhttp3.*

open class IPFSConnection constructor(val base_url: String, val okHttpClient: OkHttpClient, val moshi: Moshi) {

    var lastError: MessageWithCode? = null
    val errorAdapter: JsonAdapter<MessageWithCode> by lazy { moshi.adapter(MessageWithCode::class.java) }

    fun callCmd(cmd: String): ResponseBody {
        val request = Request.Builder()
                .url(base_url+ cmd)
                .build();

        return okHttpClient.newCall(request).execute().body();
    }

    fun setErrorByJSON(jsonString: String) {
        lastError = errorAdapter.fromJson(jsonString)
    }
}