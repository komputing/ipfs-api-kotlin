package io.ipfs.kotlin.model

@kotlinx.serialization.Serializable
data class MessageWithCode(val Message: String,
                           val Code: Int)