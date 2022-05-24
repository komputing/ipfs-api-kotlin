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
                api("com.squareup.okhttp3:okhttp:${Versions.okhttp}")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.mockito:mockito-core:3.3.3")
                implementation("org.mockito:mockito-core:4.5.1")
                implementation("com.squareup.okhttp3:mockwebserver:${Versions.okhttp}")
                implementation("org.assertj:assertj-core:3.22.0")
            }
        }
    }
}
