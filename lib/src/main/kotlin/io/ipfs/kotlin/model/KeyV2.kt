package io.ipfs.kotlin.model

import com.squareup.moshi.Json

data class KeyV2Content(@Json(name = "/") val hash: String)
data class KeyV2(val Key: KeyV2Content)