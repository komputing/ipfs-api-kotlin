package io.ipfs.kotlin.defaults

import io.ipfs.kotlin.IPFS
import io.ipfs.kotlin.IPFSConfiguration

val localIPFSConfig by lazy {
    IPFSConfiguration("http://127.0.0.1:5001/api/v0/", createOKHTTP())
}

open class LocalIPFS : IPFS(localIPFSConfig)