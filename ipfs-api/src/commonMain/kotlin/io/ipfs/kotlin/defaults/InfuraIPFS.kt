package io.ipfs.kotlin.defaults

import io.ipfs.kotlin.IPFS
import io.ipfs.kotlin.IPFSConfiguration

//https://docs.infura.io/infura/networks/ipfs/how-to/authenticate-requests
fun infuraIPFSConfig(projectId: String, projectSecret: String) =
    IPFSConfiguration(
        "https://ipfs.infura.io:5001/api/v0/",
        basicAuthCredentials = IPFSConfiguration.BasicAuth(projectId, projectSecret)
    )


open class InfuraIPFS(projectId: String, projectSecret: String) : IPFS(infuraIPFSConfig(projectId, projectSecret))