package io.ipfs.kotlin.defaults

import okhttp3.OkHttpClient

internal  fun createOKHTTP() = OkHttpClient.Builder().build()
