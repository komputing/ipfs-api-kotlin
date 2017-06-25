package io.ipfs.kotlin.commands

import com.squareup.moshi.JsonAdapter
import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.VersionInfo

class Info(val ipfs: IPFSConnection) {

    private val versionAdapter: JsonAdapter<VersionInfo> = ipfs.moshi.adapter(VersionInfo::class.java)

    fun version(): VersionInfo? {
        val response = ipfs.callCmd("version")
       return response.use { versionAdapter.fromJson(it.source()) }
    }

}