package io.ipfs.example_cli

import io.ipfs.kotlin.IPFS
import io.ipfs.kotlin.IPFSConfiguration

suspend fun main() {
    println(IPFS(IPFSConfiguration()).info.version())
}