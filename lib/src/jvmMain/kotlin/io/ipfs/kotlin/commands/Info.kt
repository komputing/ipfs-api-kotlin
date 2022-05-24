package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.VersionInfo

class Info(val ipfs: IPFSConnection) {

    private val versionAdapter= ipfs.config.moshi.adapter(VersionInfo::class.java)

    fun version(): VersionInfo? {
        val response = ipfs.callCmd("version")
       return response.use { versionAdapter.fromJson(it.source()) }
    }

}