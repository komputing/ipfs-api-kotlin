package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.Key
import io.ipfs.kotlin.model.KeyV2
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Repo(val ipfs: IPFSConnection) {

    suspend fun gc() = ipfs.callCmd("repo/gc").let { listFromNDJson(it) }

    suspend fun listFromNDJson(source: HttpResponse): List<String> {
        val jsonString = source.bodyAsText()
        return try {
            jsonString.parseNDJSON { it?.let { Json.decodeFromString<KeyV2>(it).Key.hash } }
        } catch (e: Throwable) {
            jsonString.parseNDJSON { Json.decodeFromString<Key>(it!!).Key }
        }
    }

    // http://ndjson.org
    private fun String.parseNDJSON(convert: (input: String?) -> String?) = replace("\r", "").split("\n").asSequence().filter { !it.isEmpty() }.map {
        convert(it)
    }.filterNotNull().toList()
}