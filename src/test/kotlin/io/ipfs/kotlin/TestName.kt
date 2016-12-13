package io.ipfs.kotlin

import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TestName() : BaseIPFSWebserverTest() {

    @Test
    fun testPublishSuccess() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Name\":\"hashname\",\"Value\":\"hashvalue\"}\n"))

        // invoke
        val result = ipfs.name.publish("hashvalue")

        // assert
        assertThat(result).isEqualTo("hashname")

        val executedRequest = server.takeRequest()
        assertThat(executedRequest.path).isEqualTo("/name/publish/hashvalue")

    }

    @Test
    fun testPublishFail() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Message\":\"invalid ipfs ref path\",\"Code\":0}"))

        // invoke
        val result = ipfs.name.publish("hashname")

        // assert
        assertThat(result).isEqualTo(null)

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).isEqualTo("/name/publish/hashname")

        assertThat(ipfs.lastError!!.Message).isEqualTo("invalid ipfs ref path")

    }

    @Test
    fun testResolveSuccess() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Path\":\"/ipfs/somehash\"}\n"));

        // invoke
        val result = ipfs.name.resolve("somehash")

        // assert
        assertThat(result).isEqualTo("/ipfs/somehash")

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).isEqualTo("/name/resolve/somehash");

    }

    @Test
    fun testResolveFail() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Message\":\"Could not resolve name.\",\"Code\":0}"));

        // invoke
        val result = ipfs.name.resolve("somehash")

        // assert
        assertThat(result).isEqualTo(null)

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).isEqualTo("/name/resolve/somehash");

        assertThat(ipfs.lastError!!.Message).isEqualTo("Could not resolve name.")
    }


}