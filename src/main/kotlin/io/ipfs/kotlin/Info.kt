package io.ipfs.kotlin

import com.squareup.moshi.JsonAdapter
import okhttp3.*

class Info(val ipfs: IPFS) {

    fun version(): VersionInfo {

        val request = Request.Builder()
                .url("${ipfs.base_url}version")
                .build();

        val response = ipfs.okHttpClient.newCall(request).execute();
        val adapter: JsonAdapter<VersionInfo> = ipfs.moshi.adapter(VersionInfo::class.java)
        return adapter.fromJson(response.body().string())
    }

}