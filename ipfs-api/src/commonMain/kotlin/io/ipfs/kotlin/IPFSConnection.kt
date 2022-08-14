package io.ipfs.kotlin

import io.ipfs.kotlin.model.MessageWithCode
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

open class IPFSConnection(val config: IPFSConfiguration) {

    var lastError: MessageWithCode? = null

    private fun buildRequest(cmd: String, block: HttpRequestBuilder.() -> Unit = {}): HttpRequestBuilder {
        val request = HttpRequestBuilder().apply {
            url(config.base_url + cmd)
            contentType(ContentType.Any)
            config.basicAuthCredentials?.apply {
                basicAuth(username, password)
            }
            block()
        }

        return request
    }

    suspend fun callCmd(cmd: String): HttpResponse {
        val request = buildRequest(cmd)
        return config.ktorClient.post(request)
    }

    suspend fun prepareCallCmd(cmd: String, block: HttpRequestBuilder.() -> Unit = {}): HttpStatement {
        val request = buildRequest(cmd, block)
        return config.ktorClient.preparePost(request)
    }

    fun setErrorByJSON(jsonString: String) {
        lastError = Json.decodeFromString(jsonString)
    }
}