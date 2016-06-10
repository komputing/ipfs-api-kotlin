package io.ipfs.kotlin

import com.squareup.moshi.Moshi
import io.ipfs.kotlin.commands.Add
import io.ipfs.kotlin.commands.Get
import io.ipfs.kotlin.commands.Info
import io.ipfs.kotlin.commands.Stats
import okhttp3.*

open class IPFS @JvmOverloads constructor(protected val base_url: String = "http://127.0.0.1:5001/api/v0/",
                                          protected val okHttpClient: OkHttpClient = OkHttpClient.Builder().build(),
                                          protected val moshi: Moshi = Moshi.Builder().build()) {

    protected val connection =  IPFSConnection(base_url,okHttpClient,moshi)

    val add by lazy { Add(connection) }
    val get by lazy { Get(connection) }
    val info by lazy { Info(connection) }
    val stats by lazy { Stats(connection) }
}