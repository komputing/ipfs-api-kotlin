package io.ipfs.example_cli

import io.ipfs.kotlin.commands.AddProgress
import io.ipfs.kotlin.commands.UploadProgress
import io.ipfs.kotlin.defaults.InfuraIPFS
import kotlinx.coroutines.runBlocking
import okio.Path.Companion.toOkioPath
import java.io.File

fun main(args: Array<String>) = runBlocking {
    require(args.size == 3)
    val projectId = args[0]
    val projectSecret = args[1]
    val filePath = args[2]
    val ipfs = InfuraIPFS(projectId, projectSecret)
    println(ipfs.info.version())
    println(
        ipfs.add.file(
            File(filePath).toOkioPath()
        ) { uploadProgress: UploadProgress?, addProgress: AddProgress? ->
            uploadProgress?.let { println(it) }
            addProgress?.let { println(it) }
        }
    )
}