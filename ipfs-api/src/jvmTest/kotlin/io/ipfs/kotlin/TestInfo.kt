package io.ipfs.kotlin

import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TestInfo : BaseIPFSWebserverTest() {

    @Test
    fun testInfo() = runTest {
        // setup
        server.enqueue(
            MockResponse().setHeader("Content-Type", ContentType.Application.Json)
                .setBody("{\"Version\":\"0.4.2\",\"Commit\":\"1654bbf\",\"Repo\":\"3\"}\n")
        )

        // invoke
        val addString = ipfs.info.version()

        // assert
        assertThat(addString).isNotNull()
        assertThat(addString!!.Version).isEqualTo("0.4.2")
        assertThat(addString.Commit).isEqualTo("1654bbf")
        assertThat(addString.Repo).isEqualTo("3")

        val executedRequest = server.takeRequest()
        assertThat(executedRequest.path).isEqualTo("/version")

    }
}