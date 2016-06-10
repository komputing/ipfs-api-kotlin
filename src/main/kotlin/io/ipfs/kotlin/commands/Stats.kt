package io.ipfs.kotlin.commands

import com.squareup.moshi.JsonAdapter
import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.BandWidthInfo

class Stats(val ipfs: IPFSConnection) {

    val versionAdapter: JsonAdapter<BandWidthInfo> = ipfs.moshi.adapter(BandWidthInfo::class.java)

    fun bandWidth(): BandWidthInfo {
        val response = ipfs.callURL("${ipfs.base_url}stats/bw")
        return versionAdapter.fromJson(response.source())
    }

}