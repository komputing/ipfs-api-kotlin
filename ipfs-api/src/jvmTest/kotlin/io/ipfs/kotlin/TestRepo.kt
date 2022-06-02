package io.ipfs.kotlin

import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TestRepo : BaseIPFSWebserverTest() {

    @Test
    fun testEmptyGC() = runTest {
        // setup
        server.enqueue(MockResponse().setBody(""))

        // invoke
        val addString = ipfs.repo.gc()

        // assert
        assertThat(addString).isEmpty()

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).startsWith("/repo/gc")

    }

    @Test
    fun testGC() = runTest {
        // setup
        server.enqueue(
            MockResponse().setHeader("Content-Type", ContentType.Application.Json)
                .setBody("{\"Key\":\"k1\"}\n{\"Key\":\"k2\"}\n{\"Key\":\"k3\"}\n")
        )

        // invoke
        val addString = ipfs.repo.gc()

        // assert
        assertThat(addString).containsExactly("k1", "k2", "k3")

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).startsWith("/repo/gc")
    }

    @Test
    fun testNewGCResponseStyle() = runTest {
        // setup
        server.enqueue(
            MockResponse().setHeader("Content-Type", ContentType.Application.Json)
                .setBody("{\"Key\":{\"/\":\"k1\"}}\n{\"Key\":{\"/\":\"k2\"}}\n{\"Key\":{\"/\":\"k3\"}}\n")
        )

        // invoke
        val addString = ipfs.repo.gc()

        // assert
        assertThat(addString).containsExactly("k1", "k2", "k3")

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).startsWith("/repo/gc")
    }

    @Test
    fun testDifferentSeparator() = runTest {
        // setup
        server.enqueue(
            MockResponse().setHeader("Content-Type", ContentType.Application.Json)
                .setBody("{\"Key\":\"k1\"}\r\n{\"Key\":\"k2\"}\r\n{\"Key\":\"k3\"}")
        )

        // invoke
        val addString = ipfs.repo.gc()

        // assert
        assertThat(addString).containsExactly("k1", "k2", "k3")

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).startsWith("/repo/gc")
    }

}