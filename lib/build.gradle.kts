plugins {
    kotlin("multiplatform")
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
                // we can't yet use moshi 1.5.0 as we need @Json https://github.com/square/moshi/issues/315
                api("com.squareup.moshi:moshi:1.4.0")
                api("com.squareup.okhttp3:okhttp:${Versions.okhttp}")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.mockito:mockito-core:3.3.3")
                implementation("com.squareup.okhttp3:mockwebserver:${Versions.okhttp}")
                implementation("org.assertj:assertj-core:3.10.0")
            }
        }
    }
}
