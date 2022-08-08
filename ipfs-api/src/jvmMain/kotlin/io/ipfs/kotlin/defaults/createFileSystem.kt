package io.ipfs.kotlin.defaults

import okio.FileSystem

internal actual fun createFileSystem(): FileSystem = FileSystem.SYSTEM
