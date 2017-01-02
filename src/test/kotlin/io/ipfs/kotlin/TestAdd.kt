package io.ipfs.kotlin

import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import org.assertj.core.api.Assertions.*;
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

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
    fun testAddFile() {
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

    @Test
    fun testAddDirectory() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Hash\":\"hashprobe\",\"Name\":\"nameprobe\"}"));

        // create nested subdirectories
        val path = Files.createTempDirectory("temptestdir")
        val dttf = File.createTempFile("dirtemptestfile", null, path.toFile())
        dttf.writeText("Contents of temptestdir/dirtemptestfile")
        val subdirpath = Files.createDirectory(Paths.get(path.toString()+File.separator+"subdir"))
        val sdttf = File.createTempFile("subdirtemptestfile", null,subdirpath.toFile())
        sdttf.writeText("Contents of temptestdir/subdir/subdirtemptestfile")
        val dttf2 = File.createTempFile("dirtemptestfile2", null, path.toFile())
        dttf2.writeText("Contents of temptestdir/dirtemptestfile2")

        val result = ipfs.add.directory(path.toFile(),path.fileName.toString())

        // assert
        assertThat(result.first().Hash).isEqualTo("hashprobe")
        assertThat(result.first().Name).isEqualTo("nameprobe")

        val executedRequest = server.takeRequest()
        val body = executedRequest.body.readUtf8()
        assertThat(executedRequest.path).startsWith("/add")
        assertThat(body).containsPattern(""".*temptestdir.*""")
    }


}
