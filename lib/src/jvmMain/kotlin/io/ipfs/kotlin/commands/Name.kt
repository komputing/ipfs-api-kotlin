package io.ipfs.kotlin.commands

import com.squareup.moshi.JsonAdapter
import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.NameValue
import io.ipfs.kotlin.model.Path

class Name(val ipfs: IPFSConnection) {

    private val adapter: JsonAdapter<NameValue> by lazy {
        ipfs.config.moshi.adapter(NameValue::class.java)
    }

    private val pathAdapter: JsonAdapter<Path> by lazy {
        ipfs.config.moshi.adapter(Path::class.java)
    }

    fun publish(hash: String): String? {
        val resultString = ipfs.callCmd("name/publish/$hash").use { it.string() }
        val resultBoolean = resultString.contains(hash)
        if (!resultBoolean) {
            ipfs.setErrorByJSON(resultString)
            return null
        }
        return adapter.fromJson(resultString)?.Name
    }

    fun resolve(hash: String): String? {
        val resultString = ipfs.callCmd("name/resolve/$hash").use { it.string() }

        return when {
            resultString == null -> null
            resultString.contains("Path") -> pathAdapter.fromJson(resultString)?.Path
            else -> {
                ipfs.setErrorByJSON(resultString)
                null
            }
        }
    }

}