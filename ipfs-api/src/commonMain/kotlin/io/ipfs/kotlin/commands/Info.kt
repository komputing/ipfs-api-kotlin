package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.VersionInfo
import io.ktor.client.call.*

class Info(val ipfs: IPFSConnection) {


    suspend fun version(): VersionInfo? {
        return try {
            ipfs.callCmd("version").body() as VersionInfo
        } catch (_: Throwable){
            null
        }
    }

}