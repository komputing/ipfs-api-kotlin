package io.ipfs.kotlin

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.assertj.core.api.Assertions.*;
import org.junit.After
import org.junit.Before
import java.io.File

class TestInfo() {

    val server = MockWebServer();
    val ipfs: IPFS by lazy { IPFS(OkHttpClient.Builder().build(), server.url("").toString()) }

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
        server.enqueue(MockResponse().setBody("{\"Version\":\"0.4.2\",\"Commit\":\"1654bbf\",\"Repo\":\"3\"}\n"));

        // invoke
        val addString = ipfs.info.version()!!

        // assert
        assertThat(addString.Version).isEqualTo("0.4.2")
        assertThat(addString.Commit).isEqualTo("1654bbf")
        assertThat(addString.Repo).isEqualTo("3")

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).isEqualTo("/version");

    }


}