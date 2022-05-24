package io.ipfs.kotlin.model

@kotlinx.serialization.Serializable
data class VersionInfo(val Version: String,
                       val Commit: String,
                       val Repo: String)