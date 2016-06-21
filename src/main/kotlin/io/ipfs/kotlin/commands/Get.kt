package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection

class Get(val ipfs: IPFSConnection) {

    fun cat(hash: String): String {
        val response = ipfs.callCmd("cat/$hash")
        val result = response.string()
        response.close()
        return result;
    }

}