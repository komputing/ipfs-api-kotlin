package io.ipfs.kotlin.model

data class BandWidthInfo(val TotalIn: Long,
                         val TotalOut: Long,
                         val RateIn: Double,
                         val RateOut: Double)