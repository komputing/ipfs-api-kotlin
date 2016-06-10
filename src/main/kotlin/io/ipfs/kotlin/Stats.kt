package io.ipfs.kotlin

import com.squareup.moshi.JsonAdapter

class Stats(val ipfs: IpfsConnection) {

    val versionAdapter: JsonAdapter<BandWidthInfo> = ipfs.moshi.adapter(BandWidthInfo::class.java)

    fun bandWidth(): BandWidthInfo {
        val response = ipfs.callURL("${ipfs.base_url}stats/bw")
        return versionAdapter.fromJson(response.source())
    }

}