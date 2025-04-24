package io.ipfs.kotlin

import io.ipfs.kotlin.defaults.localIPFSConfig
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

abstract class BaseIPFSWebserverTest {

    val server = MockWebServer()
    val ipfs: IPFS by lazy {
        IPFS(localIPFSConfig.copy(base_url = server.url("").toString()))
    }

    @BeforeEach
    fun runBeforeEveryTest() {
        server.start()
    }

    @AfterEach
    fun runAfterEveryTest() {
        server.shutdown()
    }
}