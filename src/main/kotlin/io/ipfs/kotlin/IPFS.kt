package io.ipfs.kotlin

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.ipfs.kotlin.NamedHash
import okhttp3.*
import java.io.File

open class IPFS(val okHttpClient: OkHttpClient, val base_url: String = "http://127.0.0.1:5001/api/v0/") {

    val moshi: Moshi by lazy { Moshi.Builder().build() }

    val adapter: JsonAdapter<NamedHash> by lazy { moshi.adapter(NamedHash::class.java) }

    val add by lazy { Add(this) }
    val info by lazy { Info(this) }

}