package io.ipfs.kotlin

import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TestSwarm : BaseIPFSWebserverTest() {

    @Test
    fun testSwarmConnect() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Strings\":[\"connect Qma8ddFEQWEU8ijWvdxXm3nxU7oHsRtCykAaVz8WUYhiKn success\"]}\n"))

        // invoke
        val addString = ipfs.swarm.connect("/ip4/137.184.243.187/tcp/3000/ws/p2p/Qma8ddFEQWEU8ijWvdxXm3nxU7oHsRtCykAaVz8WUYhiKn")

        // assert
        assertThat(addString).isNotNull()

        val executedRequest = server.takeRequest()
        assertThat(executedRequest.path).isEqualTo("/swarm/connect?args=/ip4/137.184.243.187/tcp/3000/ws/p2p/Qma8ddFEQWEU8ijWvdxXm3nxU7oHsRtCykAaVz8WUYhiKn")

    }
}