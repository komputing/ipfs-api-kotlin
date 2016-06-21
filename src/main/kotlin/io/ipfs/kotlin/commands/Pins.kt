package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection

class Pins(val ipfs: IPFSConnection) {

    fun add(hash: String): Boolean {
        val response = ipfs.callCmd("pin/add/$hash")
        val resultString = response.string()
        response.close()
        val resultBoolean = resultString.contains(hash)
        if (!resultBoolean) {
            ipfs.setErrorByJSON(resultString)
        }
        return resultBoolean
    }

}