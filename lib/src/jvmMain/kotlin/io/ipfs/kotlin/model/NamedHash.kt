package io.ipfs.kotlin.model

@kotlinx.serialization.Serializable
data class NamedHash(val Name: String,
                     val Hash: String)