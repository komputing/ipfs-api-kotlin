package io.ipfs.kotlin

import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import org.assertj.core.api.Assertions.*;
import java.io.File

class TestAdd() :BaseIPFSWebserverTest() {

    @Test
    fun testAddString() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Hash\":\"hashprobe\",\"Name\":\"nameprobe\"}"));

        // invoke
        val addString = ipfs.add.string("foo")

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
        val addString = ipfs.add.file(File.createTempFile("temptestfile", null))

        // assert
        assertThat(addString.Hash).isEqualTo("hashprobe")
        assertThat(addString.Name).isEqualTo("nameprobe")

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).startsWith("/add");

    }


}