package io.ipfs.kotlin.model

import kotlinx.serialization.SerialName


@kotlinx.serialization.Serializable
data class NamedResponse(
    @SerialName("Name")
    val name: String,
    @SerialName("Bytes")
    val bytes: Long? = null,
    @SerialName("Hash")
    val hash: String? = null,
    @SerialName("Size")
    val size: String? = null
)
