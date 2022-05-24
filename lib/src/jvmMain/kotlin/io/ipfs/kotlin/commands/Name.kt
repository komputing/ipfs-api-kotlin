package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.NameValue
import io.ipfs.kotlin.model.Path
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Name(val ipfs: IPFSConnection) {

    fun publish(hash: String): String? {
        val resultString = ipfs.callCmd("name/publish/$hash").use { it.string() }
        val resultBoolean = resultString.contains(hash)
        if (!resultBoolean) {
            ipfs.setErrorByJSON(resultString)
            return null
        }
        return Json.decodeFromString<NameValue>(resultString).Name
    }

    fun resolve(hash: String): String? {
        val resultString = ipfs.callCmd("name/resolve/$hash").use { it.string() }

        return when {
            resultString == null -> null
            resultString.contains("Path") -> Json.decodeFromString<Path>(resultString).Path
            else -> {
                ipfs.setErrorByJSON(resultString)
                null
            }
        }
    }

}