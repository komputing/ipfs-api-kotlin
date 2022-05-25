package io.ipfs.example_cli

import io.ipfs.kotlin.IPFS
import io.ipfs.kotlin.IPFSConfiguration

fun main() = runBlocking {
    println(IPFS(IPFSConfiguration()).info.version())
}