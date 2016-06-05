package io.ipfs.kotlin

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

abstract class BaseIPFSWebserverTest() {

    val server = MockWebServer();
    val ipfs: IPFS by lazy { IPFS(base_url = server.url("").toString()) }

    @Before
    fun runBeforeEveryTest() {
        server.start()
    }

    @After
    fun runAfterEveryTest() {
        server.shutdown()
    }
}