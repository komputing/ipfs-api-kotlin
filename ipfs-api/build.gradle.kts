plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("jacoco")
    id("com.github.ben-manes.versions")
    id("maven-publish")
}

group = "com.github.ligi"
version = "0.16"


repositories {
    mavenCentral()
}

kotlin {
    val darwinTargets = arrayOf(
        "macosX64", "macosArm64",
        "iosArm64", "iosX64", "iosSimulatorArm64",
        "tvosArm64", "tvosX64", "tvosSimulatorArm64",
        "watchosArm32", "watchosArm64", "watchosX86", "watchosX64", "watchosSimulatorArm64",
    )
    val linuxTargets = arrayOf("linuxX64", /*"linuxArm64"*/)
    val mingwTargets = arrayOf("mingwX64")
    val nativeTargets = linuxTargets + darwinTargets + mingwTargets

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    js(IR) {
        nodejs()
    }
    for (target in nativeTargets) {
        targets.add(presets.getByName(target).createTarget(target))
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}")
                api("io.ktor:ktor-client-core:${Versions.ktor}")
                implementation("io.ktor:ktor-client-content-negotiation:${Versions.ktor}")
                implementation("io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}")
                api("com.squareup.okio:okio:${Versions.okio}")
            }
        }

        val jvmMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:${Versions.ktor}")
            }
        }

        val jsMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-js:${Versions.ktor}")
                implementation("com.squareup.okio:okio-nodefilesystem:${Versions.okio}")
            }
        }


        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}")
                implementation("org.mockito:mockito-core:4.5.1")
                implementation("com.squareup.okhttp3:mockwebserver:${Versions.okhttp}")
                implementation("org.assertj:assertj-core:3.22.0")
            }
        }
        val nativeMain by creating {
            dependsOn(commonMain)
        }

        val darwinMain by creating {
            dependsOn(nativeMain)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:${Versions.ktor}")
            }
        }
        darwinTargets.forEach { target ->
            getByName("${target}Main") {
                dependsOn(darwinMain)
            }
        }
        val linuxAndMingwMain by creating {
            dependsOn(nativeMain)
            dependencies {
                implementation("io.ktor:ktor-client-curl:${Versions.ktor}")
            }
        }
        (mingwTargets + linuxTargets).forEach { target ->
            getByName("${target}Main") {
                dependsOn(linuxAndMingwMain)
            }
        }

    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}