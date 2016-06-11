package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection

class Get(val ipfs: IPFSConnection) {

    fun cat(hash: String): String {
        return ipfs.callCmd("cat/$hash").string();
    }

}