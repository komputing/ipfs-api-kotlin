package io.ipfs.kotlin.defaults

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient


internal fun createOKHTTP() = OkHttpClient.Builder().build()

@OptIn(ExperimentalSerializationApi::class)
internal fun createKTOR() = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
}


