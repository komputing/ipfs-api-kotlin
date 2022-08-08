package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.NameValue
import io.ipfs.kotlin.model.Path
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Name(val ipfs: IPFSConnection) {

    suspend fun publish(hash: String): String? {
        val resultString = ipfs.callCmd("name/publish/$hash").let { it.bodyAsText() }
        val resultBoolean = resultString.contains(hash)
        if (!resultBoolean) {
            ipfs.setErrorByJSON(resultString)
            return null
        }
        return Json.decodeFromString<NameValue>(resultString).Name
    }

    suspend fun resolve(hash: String): String? {
        val resultString = ipfs.callCmd("name/resolve/$hash").bodyAsText()

        return when {
            resultString.isBlank() -> null
            resultString.contains("Path") -> Json.decodeFromString<Path>(resultString).Path
            else -> {
                ipfs.setErrorByJSON(resultString)
                null
            }
        }
    }

}