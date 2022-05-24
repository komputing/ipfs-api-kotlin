package io.ipfs.kotlin

import io.ipfs.kotlin.model.MessageWithCode
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody

open class IPFSConnection(val config: IPFSConfiguration) {

    var lastError: MessageWithCode? = null

    fun callCmd(cmd: String): ResponseBody {
        val request = Request.Builder()
                .post(RequestBody.create(null, ""))
                .url(config.base_url + cmd)
                .build()

        return config.okHttpClient.newCall(request).execute().body!!
    }

    fun setErrorByJSON(jsonString: String) {
        lastError = Json.decodeFromString(jsonString)
    }
}