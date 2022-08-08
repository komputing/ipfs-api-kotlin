package io.ipfs.kotlin.defaults

import io.ipfs.kotlin.IPFS
import io.ipfs.kotlin.IPFSConfiguration

val infuraIPFSConfig by lazy {
    IPFSConfiguration("https://ipfs.infura.io:5001/api/v0/")
}

open class InfuraIPFS : IPFS(infuraIPFSConfig)