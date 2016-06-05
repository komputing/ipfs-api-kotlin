package io.ipfs.kotlin

import com.squareup.moshi.JsonAdapter
import okhttp3.*

class Info(val ipfs: IPFS) {

    val adapter: JsonAdapter<VersionInfo> by lazy { ipfs.moshi.adapter(VersionInfo::class.java) }

    fun version(): VersionInfo? {

        val request = Request.Builder()
                .url("${ipfs.base_url}version")
                .build();

        val response = ipfs.okHttpClient.newCall(request).execute();
        return adapter.fromJson(response.body().string())
    }

}