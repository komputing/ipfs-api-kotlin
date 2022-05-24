package io.ipfs.kotlin

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TestGet : BaseIPFSWebserverTest() {

    @Test
    fun testAddString() = runTest {
        // setup
        server.enqueue(MockResponse().setBody("result"))

        // invoke
        val result = ipfs.get.cat("hash")

        // assert
        assertThat(result).isEqualTo("result")

        val executedRequest = server.takeRequest()
        assertThat(executedRequest.path).startsWith("/cat/hash")

    }

}