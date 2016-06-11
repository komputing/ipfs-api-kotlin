package io.ipfs.kotlin.commands

import com.squareup.moshi.JsonAdapter
import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.BandWidthInfo

class Stats(val ipfs: IPFSConnection) {

    private val bandWidthAdapter: JsonAdapter<BandWidthInfo> = ipfs.moshi.adapter(BandWidthInfo::class.java)

    fun bandWidth(): BandWidthInfo {
        val response = ipfs.callCmd("stats/bw")
        return bandWidthAdapter.fromJson(response.source())
    }

}