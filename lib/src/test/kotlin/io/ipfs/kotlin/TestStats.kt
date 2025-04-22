package io.ipfs.kotlin

import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TestStats : BaseIPFSWebserverTest() {

    @Test
    fun testBandWidthStats() {
        // setup
        server.enqueue(MockResponse().setBody("{\"TotalIn\":80461165,\"TotalOut\":70998948,\"RateIn\":1103.8830769540511,\"RateOut\":1814.6417381019044}\n"))

        // invoke
        val statsBandWidth = ipfs.stats.bandWidth()

        // assert
        assertThat(statsBandWidth).isNotNull()
        assertThat(statsBandWidth!!.TotalIn).isEqualTo(80461165)
        assertThat(statsBandWidth.TotalOut).isEqualTo(70998948)
        assertThat(statsBandWidth.RateIn).isEqualTo(1103.8830769540511)
        assertThat(statsBandWidth.RateOut).isEqualTo(1814.6417381019044)

        val executedRequest = server.takeRequest()
        assertThat(executedRequest.path).isEqualTo("/stats/bw")

    }
}