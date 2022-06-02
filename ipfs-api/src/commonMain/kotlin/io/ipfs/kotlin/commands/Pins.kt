package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import io.ktor.client.statement.*

class Pins(val ipfs: IPFSConnection) {

    suspend fun add(hash: String): Boolean {
        val resultString = ipfs.callCmd("pin/add/$hash").let { it.bodyAsText() }
        val resultBoolean = resultString.contains(hash)
        if (!resultBoolean) {
            ipfs.setErrorByJSON(resultString)
        }
        return resultBoolean
    }

}