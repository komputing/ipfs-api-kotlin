package io.ipfs.kotlin.commands

import com.squareup.moshi.JsonAdapter
import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.Strings

class Swarm(val ipfs: IPFSConnection) {

    private val stringsAdapter: JsonAdapter<Strings> by lazy {
        ipfs.config.moshi.adapter(Strings::class.java)
    }

    fun connect(address: String): KuboResult {
        val httpResponse = ipfs.executeCmd("swarm/connect?arg=$address")
        if (httpResponse.isSuccessful) {
            val result = stringsAdapter.fromJson(httpResponse.body()!!.use {
                it.string()
            })
            return KuboResult.Success(result)
        } else {
            ipfs.setErrorByJSON(httpResponse.body()!!.use { responseBody ->
                responseBody.string()
            })
            return KuboResult.Failure(ipfs.lastError?.Message ?: "Unknown error")
        }
    }
}

sealed class KuboResult {
    data class Success(val result: Strings) : KuboResult()
    data class Failure(val errorMessage: String) : KuboResult()
}