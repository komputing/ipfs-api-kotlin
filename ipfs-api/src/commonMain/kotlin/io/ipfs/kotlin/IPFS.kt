package io.ipfs.kotlin

import io.ipfs.kotlin.commands.*
import io.ipfs.kotlin.defaults.createFileSystem
import io.ipfs.kotlin.defaults.createKTOR
import io.ipfs.kotlin.model.MessageWithCode
import io.ktor.client.*
import okio.FileSystem

data class IPFSConfiguration(
    val base_url: String = "http://127.0.0.1:5001/api/v0/",
    val ktorClient: HttpClient = createKTOR(),
    val fileSystem: FileSystem = createFileSystem(),
    val basicAuthCredentials: BasicAuth? = null
) {
    class BasicAuth(
        val username: String,
        val password: String
    )
}

open class IPFS(configuration: IPFSConfiguration) {

    private val connection = IPFSConnection(configuration)

    val add by lazy { Add(connection) }
    val get by lazy { Get(connection) }
    val info by lazy { Info(connection) }
    val stats by lazy { Stats(connection) }
    val pins by lazy { Pins(connection) }
    val repo by lazy { Repo(connection) }
    val name by lazy { Name(connection) }

    val lastError: MessageWithCode? get() = connection.lastError
}