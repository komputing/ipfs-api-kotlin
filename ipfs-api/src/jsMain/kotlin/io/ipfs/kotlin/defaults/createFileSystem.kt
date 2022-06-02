package io.ipfs.kotlin.defaults

import okio.FileSystem
import okio.NodeJsFileSystem

internal actual fun createFileSystem(): FileSystem = NodeJsFileSystem
