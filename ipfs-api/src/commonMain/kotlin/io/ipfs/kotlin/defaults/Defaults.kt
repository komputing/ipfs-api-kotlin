package io.ipfs.kotlin.defaults

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import okio.FileSystem


internal fun createKTOR() = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
    install(HttpTimeout) {
        // this socketTimeout can occur w/ okhttp engine when posting larger file
        socketTimeoutMillis = 600_000 // 10 minutes
    }
}

expect internal fun createFileSystem(): FileSystem


