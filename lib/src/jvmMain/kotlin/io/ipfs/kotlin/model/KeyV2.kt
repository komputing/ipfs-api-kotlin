package io.ipfs.kotlin.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class KeyV2Content(@SerialName("/") val hash: String)
@kotlinx.serialization.Serializable
data class KeyV2(val Key: KeyV2Content)