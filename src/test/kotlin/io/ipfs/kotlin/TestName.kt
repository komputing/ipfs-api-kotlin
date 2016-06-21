package io.ipfs.kotlin

import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import org.assertj.core.api.Assertions.*;

class TestName() : BaseIPFSWebserverTest() {

    @Test
    fun testPublishSuccess() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Name\":\"hashname\",\"Value\":\"hashvalue\"}\n"));

        // invoke
        val result = ipfs.name.publish("hashvalue")

        // assert
        assertThat(result).isEqualTo("hashname")

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).isEqualTo("/name/publish/hashvalue");

    }

    @Test
    fun testPublishFail() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Message\":\"invalid ipfs ref path\",\"Code\":0}"));

        // invoke
        val result = ipfs.name.publish("hashname")

        // assert
        assertThat(result).isEqualTo(null)

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).isEqualTo("/name/publish/hashname");

        assertThat(ipfs.lastError!!.Message).isEqualTo("invalid ipfs ref path")

    }

}