package io.ipfs.kotlin

import com.squareup.moshi.Moshi
import okhttp3.*

open class IpfsConnection @JvmOverloads constructor(val base_url: String = "http://127.0.0.1:5001/api/v0/",
                                                    val okHttpClient: OkHttpClient = OkHttpClient.Builder().build(),
                                                    val moshi: Moshi = Moshi.Builder().build()) {

    fun callURL(url: String): ResponseBody {
        val request = Request.Builder()
                .url(url)
                .build();

        return okHttpClient.newCall(request).execute().body();
    }

}