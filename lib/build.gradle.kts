plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("jacoco")
    id("com.github.ben-manes.versions")
}

group = "com.github.ligi"

repositories {
    mavenCentral()
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
                api("io.ktor:ktor-client-core:2.0.0")
                implementation("io.ktor:ktor-client-okhttp:2.0.0")
                implementation("io.ktor:ktor-client-content-negotiation:2.0.0")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.0")
                api("com.squareup.okhttp3:okhttp:${Versions.okhttp}")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
                implementation("org.mockito:mockito-core:4.5.1")
                implementation("com.squareup.okhttp3:mockwebserver:${Versions.okhttp}")
                implementation("org.assertj:assertj-core:3.22.0")
            }
        }
    }
}
