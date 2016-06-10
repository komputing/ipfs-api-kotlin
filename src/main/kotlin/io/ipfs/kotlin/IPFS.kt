package io.ipfs.kotlin

import com.squareup.moshi.Moshi
import okhttp3.*

open class IPFS @JvmOverloads constructor(protected val base_url: String = "http://127.0.0.1:5001/api/v0/",
                                          protected val okHttpClient: OkHttpClient = OkHttpClient.Builder().build(),
                                          protected val moshi: Moshi = Moshi.Builder().build()) {

    protected val ipfsViaHttp =  IpfsConnection(base_url,okHttpClient,moshi)

    val add by lazy { Add(ipfsViaHttp) }
    val get by lazy { Get(ipfsViaHttp) }
    val info by lazy { Info(ipfsViaHttp) }
}