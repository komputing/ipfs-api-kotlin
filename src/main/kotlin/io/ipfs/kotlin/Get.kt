package io.ipfs.kotlin

import okhttp3.*

class Get(val ipfs: IPFS) {


    fun cat(hash: String): String {

        val request = Request.Builder()
                .url("${ipfs.base_url}cat/$hash")
                .build();

        return ipfs.okHttpClient.newCall(request).execute().body().string();
    }

}