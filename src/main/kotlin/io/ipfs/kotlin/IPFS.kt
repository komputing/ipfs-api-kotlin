package io.ipfs.kotlin

import com.squareup.moshi.Moshi
import okhttp3.*

open class IPFS(val base_url: String = "http://127.0.0.1:5001/api/v0/",
                val okHttpClient: OkHttpClient = OkHttpClient.Builder().build(),
                val moshi: Moshi = Moshi.Builder().build()) {

    val add by lazy { Add(this) }
    val get by lazy { Get(this) }
    val info by lazy { Info(this) }

}