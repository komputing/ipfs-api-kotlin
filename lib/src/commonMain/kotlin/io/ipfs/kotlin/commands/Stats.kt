package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.BandWidthInfo
import io.ktor.client.call.*

class Stats(val ipfs: IPFSConnection) {


    suspend fun bandWidth(): BandWidthInfo? {
        return try {
            val result : BandWidthInfo = ipfs.callCmd("stats/bw").body()
            result
        } catch (t : Throwable){
            println(t)
            null
        }
    }

}