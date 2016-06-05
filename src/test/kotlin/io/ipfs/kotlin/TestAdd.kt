package io.ipfs.kotlin

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.assertj.core.api.Assertions.*;
import org.junit.After
import org.junit.Before
import java.io.File

class TestAdd() {

    val server = MockWebServer();
    val ipfs: IPFS by lazy { IPFS(OkHttpClient.Builder().build(), server.url("").toString()) }

    @Before
    fun runBeforeEveryTest() {
        server.start()
    }

    @After
    fun runAfterEveryTest() {
        server.shutdown()
    }

    @Test
    fun testAddString() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Hash\":\"hashprobe\",\"Name\":\"nameprobe\"}"));

        // invoke
        val addString = ipfs.add.string("foo")!!

        // assert
        assertThat(addString.Hash).isEqualTo("hashprobe")
        assertThat(addString.Name).isEqualTo("nameprobe")

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).startsWith("/add");

    }

    @Test
    fun tesAddFile() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Hash\":\"hashprobe\",\"Name\":\"nameprobe\"}"));

        // invoke
        val addString = ipfs.add.file(File.createTempFile("temptestfile",null))!!

        // assert
        assertThat(addString.Hash).isEqualTo("hashprobe")
        assertThat(addString.Name).isEqualTo("nameprobe")

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).startsWith("/add");

    }


}