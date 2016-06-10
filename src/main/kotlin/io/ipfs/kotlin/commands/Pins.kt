package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection

class Pins(val ipfs: IPFSConnection) {

    fun add(hash: String): Boolean {
        val resultString = ipfs.callURL("${ipfs.base_url}pin/add/$hash").string()
        val resultBoolean = resultString.contains(hash)
        if (!resultBoolean) {
            ipfs.setErrorByJSON(resultString)
        }
        return resultBoolean
    }

}