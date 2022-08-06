rootProject.name = "ipfs-api-kotlin"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("multiplatform") version "1.7.10"
        kotlin("jvm") version "1.7.10"
        kotlin("plugin.serialization") version "1.7.10"
        id("com.github.ben-manes.versions") version "0.42.0"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include("example")
include("ipfs-api")
