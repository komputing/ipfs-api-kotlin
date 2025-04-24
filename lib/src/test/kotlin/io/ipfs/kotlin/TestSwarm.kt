package io.ipfs.kotlin

import io.ipfs.kotlin.commands.KuboResult
import io.ipfs.kotlin.model.Strings
import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TestSwarm : BaseIPFSWebserverTest() {

    @Test
    fun testSwarmConnect() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Strings\":[\"success message\"]}\n").setResponseCode(200))

        // invoke
        val connected = ipfs.swarm.connect("valid-address")

        // assert
        assertThat(connected).isNotNull()
        assertThat(connected).isEqualTo(KuboResult.Success(Strings(listOf("success message"))))

        assertThat(ipfs.lastError).isNull()

        val executedRequest = server.takeRequest()
        assertThat(executedRequest.path).isEqualTo("/swarm/connect?arg=valid-address")
    }

    @Test
    fun testSwarmConnectWithError() {
        // setup
        server.enqueue(MockResponse().setBody("""{"Message":"error message","Code":0,"Type":"error"}""").setResponseCode(500))

        // invoke
        val connected = ipfs.swarm.connect("invalid-address")

        // assert
        assertThat(connected).isNotNull()
        assertThat(connected).isEqualTo(KuboResult.Failure("error message"))

        assertThat(ipfs.lastError).isNotNull()
        assertThat(ipfs.lastError!!.Message).isEqualTo("error message")
        assertThat(ipfs.lastError!!.Code).isEqualTo(0)

        val executedRequest = server.takeRequest()
        assertThat(executedRequest.path).isEqualTo("/swarm/connect?arg=invalid-address")
    }
}