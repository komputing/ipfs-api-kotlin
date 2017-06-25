package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.Key
import okio.BufferedSource

class Repo(val ipfs: IPFSConnection) {

    fun gc(): List<String> {
        val response = ipfs.callCmd("repo/gc")
        val result = listFromNDJson(response.source())
        response.close()
        return result
    }

    fun listFromNDJson(source: BufferedSource): List<String> {
        val keyAdapter = ipfs.moshi.adapter(Key::class.java)
        // http://ndjson.org
        return source.readUtf8().replace("\r", "").split("\n").filter { !it.isEmpty() }.map {
            keyAdapter.fromJson(it)?.Key
        }.filterNotNull()
    }
}