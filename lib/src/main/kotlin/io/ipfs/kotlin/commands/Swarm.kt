package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection

class Swarm(val ipfs: IPFSConnection) {

    fun connect(address: String): Boolean {
        val resultString = ipfs.callCmd("swarm/connect?args=$address").use { it.string() }
        val resultBoolean = resultString.contains(address)
        if (!resultBoolean) {
            ipfs.setErrorByJSON(resultString)
        }
        return resultBoolean
    }

}

// {
//        "Strings":["connect Qma8ddFEQWEU8ijWvdxXm3nxU7oHsRtCykAaVz8WUYhiKn success"]
//  }