package io.ipfs.kotlin

import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import org.assertj.core.api.Assertions.*;

class TestInfo() : BaseIPFSWebserverTest() {

    @Test
    fun testInfo() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Version\":\"0.4.2\",\"Commit\":\"1654bbf\",\"Repo\":\"3\"}\n"));

        // invoke
        val addString = ipfs.info.version()

        // assert
        assertThat(addString.Version).isEqualTo("0.4.2")
        assertThat(addString.Commit).isEqualTo("1654bbf")
        assertThat(addString.Repo).isEqualTo("3")

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).isEqualTo("/version");

    }
}