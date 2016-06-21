package io.ipfs.kotlin.commands

import com.squareup.moshi.JsonAdapter
import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.NameValue
import io.ipfs.kotlin.model.Path

class Name(val ipfs: IPFSConnection) {

    private val adapter: JsonAdapter<NameValue> by lazy { ipfs.moshi.adapter(NameValue::class.java) }
    private val pathAdapter: JsonAdapter<Path> by lazy { ipfs.moshi.adapter(Path::class.java) }

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

    fun resolve(hash: String): String? {
        val response = ipfs.callCmd("name/resolve/$hash")
        val resultString = response.string()
        response.close()

        if (resultString.contains("Path")) {
            return pathAdapter.fromJson(resultString).Path
        } else {
            ipfs.setErrorByJSON(resultString)
            return null
        }
    }

}