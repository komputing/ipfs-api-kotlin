package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection

class Pins(val ipfs: IPFSConnection) {

    fun add(hash: String): Boolean {
        val resultString = ipfs.callCmd("pin/add/$hash").use { it.string() }
        val resultBoolean = resultString.contains(hash)
        if (!resultBoolean) {
            ipfs.setErrorByJSON(resultString)
        }
        return resultBoolean
    }

}