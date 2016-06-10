package io.ipfs.kotlin

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.ipfs.kotlin.model.MessageWithCode
import okhttp3.*

open class IPFSConnection constructor(val base_url: String, val okHttpClient: OkHttpClient, val moshi: Moshi) {

    var lastError: MessageWithCode? = null

    fun callURL(url: String): ResponseBody {
        val request = Request.Builder()
                .url(url)
                .build();

        return okHttpClient.newCall(request).execute().body();
    }

    val errorAdapter: JsonAdapter<MessageWithCode> by lazy { moshi.adapter(MessageWithCode::class.java) }

    fun setErrorByJSON(jsonString: String) {
        lastError = errorAdapter.fromJson(jsonString)
    }
}