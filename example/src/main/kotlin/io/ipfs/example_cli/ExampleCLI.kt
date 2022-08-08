package io.ipfs.example_cli

import io.ipfs.kotlin.commands.AddProgress
import io.ipfs.kotlin.commands.UploadProgress
import io.ipfs.kotlin.defaults.InfuraIPFS
import kotlinx.coroutines.runBlocking
import okio.Path.Companion.toOkioPath
import java.io.File

fun main(args: Array<String>) = runBlocking {
    println(InfuraIPFS().info.version())
    println(
        InfuraIPFS().add.file(
            File(args[0]).toOkioPath()
        ) { uploadProgress: UploadProgress?, addProgress: AddProgress? ->
            uploadProgress?.let { println(it) }
            addProgress?.let { println(it) }
        }
    )
}