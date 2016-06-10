package io.ipfs.kotlin

import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import org.assertj.core.api.Assertions.*;

class TestStats() : BaseIPFSWebserverTest() {

    @Test
    fun testBandWidthStats() {
        // setup
        server.enqueue(MockResponse().setBody("{\"TotalIn\":80461165,\"TotalOut\":70998948,\"RateIn\":1103.8830769540511,\"RateOut\":1814.6417381019044}\n"));

        // invoke
        val addString = ipfs.stats.bandWidth()

        // assert
        assertThat(addString.TotalIn).isEqualTo(80461165)
        assertThat(addString.TotalOut).isEqualTo(70998948)
        assertThat(addString.RateIn).isEqualTo(1103.8830769540511)
        assertThat(addString.RateOut).isEqualTo(1814.6417381019044)

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).isEqualTo("/stats/bw");

    }
}