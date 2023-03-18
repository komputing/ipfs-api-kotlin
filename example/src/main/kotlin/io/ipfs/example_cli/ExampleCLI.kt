package org.kethereum.example_cli

import io.ipfs.kotlin.IPFS
import io.ipfs.kotlin.IPFSConfiguration

fun main() {
    println(IPFS(IPFSConfiguration()).info.version())
}