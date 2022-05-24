package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.BandWidthInfo
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class Stats(val ipfs: IPFSConnection) {


    fun bandWidth(): BandWidthInfo? {
        val response = ipfs.callCmd("stats/bw")
        return response.use { Json.decodeFromStream(it.byteStream()) }
    }

}