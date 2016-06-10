package io.ipfs.kotlin

import com.squareup.moshi.JsonAdapter

class Info(val ipfs: IpfsConnection) {

    val versionAdapter: JsonAdapter<VersionInfo> = ipfs.moshi.adapter(VersionInfo::class.java)

    fun version(): VersionInfo {
        val response = ipfs.callURL("${ipfs.base_url}version")
        return versionAdapter.fromJson(response.source())
    }

}