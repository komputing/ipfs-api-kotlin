package io.ipfs.kotlin

import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TestPins : BaseIPFSWebserverTest() {

    @Test
    fun testAddPin() = runTest {
        // setup
        server.enqueue(
            MockResponse().setHeader("Content-Type", ContentType.Application.Json)
                .setBody("{\"Pins\":[\"QmPFDyWdL6yjz92jdc6bzWXHKVvydAhgTzhefSmmkDXzSZ\"]}")
        )

        // invoke
        val result = ipfs.pins.add("QmPFDyWdL6yjz92jdc6bzWXHKVvydAhgTzhefSmmkDXzSZ")

        // assert
        assertThat(result).isEqualTo(true)

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).startsWith("/pin/add/QmPFDyWdL6yjz92jdc6bzWXHKVvydAhgTzhefSmmkDXzSZ")

    }

    @Test
    fun testAddPinFail() = runTest {
        // setup
        server.enqueue(
            MockResponse().setHeader("Content-Type", ContentType.Application.Json)
                .setBody("{\"Message\":\"pin: invalid ipfs ref path\",\"Code\":0}")
        )

        // invoke
        val result = ipfs.pins.add("foo")

        // assert
        assertThat(result).isEqualTo(false)

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).startsWith("/pin/add/foo")

        assertThat(ipfs.lastError!!.Message).isEqualTo("pin: invalid ipfs ref path")

    }

}