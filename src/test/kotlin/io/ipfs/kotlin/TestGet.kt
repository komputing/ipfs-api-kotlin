package io.ipfs.kotlin

import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import org.assertj.core.api.Assertions.*;

class TestGet() : BaseIPFSWebserverTest() {

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