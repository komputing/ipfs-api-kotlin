package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.BandWidthInfo
import io.ipfs.kotlin.model.NamedHash
import io.ipfs.kotlin.model.VersionInfo
import io.ktor.client.call.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class Info(val ipfs: IPFSConnection) {


    suspend fun version(): VersionInfo? {
        return try {
            ipfs.callCmd("version").body() as VersionInfo
        } catch (_: Throwable){
            null
        }
    }

}