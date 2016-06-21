package io.ipfs.kotlin.commands

import com.squareup.moshi.JsonAdapter
import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.NameValue

class Name(val ipfs: IPFSConnection) {

    private val adapter: JsonAdapter<NameValue> by lazy { ipfs.moshi.adapter(NameValue::class.java) }

    fun publish(hash: String): String? {
        val response = ipfs.callCmd("name/publish/$hash")
        val resultString = response.string()
        response.close()
        val resultBoolean = resultString.contains(hash)
        if (!resultBoolean) {
            ipfs.setErrorByJSON(resultString)
            return null
        }
        return adapter.fromJson(resultString).Name
    }

}