package io.ipfs.kotlin.commands

import com.squareup.moshi.JsonDataException
import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.Key
import io.ipfs.kotlin.model.KeyV2
import okio.BufferedSource

class Repo(val ipfs: IPFSConnection) {

    fun gc() = ipfs.callCmd("repo/gc").use { listFromNDJson(it.source()) }

    fun listFromNDJson(source: BufferedSource): List<String> {
        val jsonString = source.readUtf8()
        try {
            val keyV2Adapter = ipfs.moshi.adapter(KeyV2::class.java)
            return jsonString.parseNDJSON({ keyV2Adapter.fromJson(it)?.Key?.hash })
        } catch (e: JsonDataException) {
            val keyAdapter = ipfs.moshi.adapter(Key::class.java)
            return jsonString.parseNDJSON({ keyAdapter.fromJson(it)?.Key })
        }
    }

    // http://ndjson.org
    fun String.parseNDJSON(convert: (input: String?) -> String?) = replace("\r", "").split("\n").filter { !it.isEmpty() }.map {
        convert(it)
    }.filterNotNull()
}