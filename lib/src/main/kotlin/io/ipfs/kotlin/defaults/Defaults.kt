package io.ipfs.kotlin.defaults

import com.squareup.moshi.Moshi
import io.ipfs.kotlin.commands.*
import io.ipfs.kotlin.model.MessageWithCode
import okhttp3.OkHttpClient

internal  fun createOKHTTP() = OkHttpClient.Builder().build()

internal fun createMoshi() = Moshi.Builder().build()