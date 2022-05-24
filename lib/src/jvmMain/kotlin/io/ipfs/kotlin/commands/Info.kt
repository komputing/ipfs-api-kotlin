package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.NamedHash
import io.ipfs.kotlin.model.VersionInfo
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class Info(val ipfs: IPFSConnection) {


    fun version(): VersionInfo? {
        val response = ipfs.callCmd("version")
       return response.use { Json.decodeFromStream(it.byteStream()) }
    }

}