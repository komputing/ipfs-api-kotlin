package io.ipfs.kotlin.model

data class BandWidthInfo(val TotalIn: Int,
                         val TotalOut: Int,
                         val RateIn: Double,
                         val RateOut: Double)