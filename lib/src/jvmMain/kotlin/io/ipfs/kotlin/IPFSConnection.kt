package io.ipfs.kotlin

import io.ipfs.kotlin.model.MessageWithCode
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

open class IPFSConnection(val config: IPFSConfiguration) {

    var lastError: MessageWithCode? = null

    suspend fun callCmd(cmd: String): HttpResponse {
        val request = HttpRequestBuilder().apply {
            url(config.base_url + cmd)
            contentType(ContentType.Any)
        }

        return config.ktorClient.post(request)
    }

    fun setErrorByJSON(jsonString: String) {
        lastError = Json.decodeFromString(jsonString)
    }
}