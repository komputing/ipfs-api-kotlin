package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection
import okhttp3.ResponseBody

class Get(val ipfs: IPFSConnection) {

    fun cat(hash: String): String = ipfs.callCmd("cat/$hash").use(ResponseBody::string)

}