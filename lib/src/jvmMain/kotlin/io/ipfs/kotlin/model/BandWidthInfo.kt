package io.ipfs.kotlin.model

@kotlinx.serialization.Serializable
data class BandWidthInfo(val TotalIn: Int,
                         val TotalOut: Int,
                         val RateIn: Double,
                         val RateOut: Double)