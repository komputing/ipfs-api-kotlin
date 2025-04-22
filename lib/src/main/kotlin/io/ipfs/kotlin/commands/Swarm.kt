package io.ipfs.kotlin.commands

import io.ipfs.kotlin.IPFSConnection

class Swarm(val ipfs: IPFSConnection) {

    fun connect(address: String): Boolean {
        val resultString = ipfs.callCmd("swarm/connect?arg=$address").use { it.string() }
        val resultBoolean = resultString.contains(address)
        if (!resultBoolean) {
            ipfs.setErrorByJSON(resultString)
        }
        return resultBoolean
    }

}

// success
// {
//  "Strings": [
//    "connect Qma8ddFEQWEU8ijWvdxXm3nxU7oHsRtCykAaVz8WUYhiKn success"
//  ]
//}

// curl -X POST "http://127.0.0.1:5001/api/v0/swarm/connect?arg=/ip4/137.184.243.187/tcp/3000/ws/p2p/Qma8ddFEQWEU8ijWvdxXm3nxU7oHsRtCykAaVz8WUYhiKn" | jq .
// {
//  "Message": "connect Qma8ddFEQWEU8ijWvdxXm3nxU7oHsRtCykAaVz8WUYhiKn failure: failed to dial: failed to dial Qma8ddFEQWEU8ijWvdxXm3nxU7oHsRtCykAaVz8WUYhiKn: all dials failed\n  * [/ip4/137.184.243.187/tcp/3000/ws] failed to negotiate security protocol: error reading handshake message: EOF",
//  "Code": 0,
//  "Type": "error"
//}

// {
//  "Message": "lookup _dnsaddr.bitswap.pinata.cloud on [::1]:53: read udp [::1]:61810->[::1]:53: read: connection refused",
//  "Code": 0,
//  "Type": "error"
//}