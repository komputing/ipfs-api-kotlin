package io.ipfs.kotlin

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.assertj.core.api.Assertions.*;
import org.junit.After
import org.junit.Before

class TestGet() {

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

    @Test
    fun testAddString() {
        // setup
        server.enqueue(MockResponse().setBody("result"));

        // invoke
        val result = ipfs.get.cat("hash")

        // assert
        assertThat(result).isEqualTo("result")

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).startsWith("/cat/hash");

    }

}