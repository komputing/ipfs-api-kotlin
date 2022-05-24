plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("io.ipfs.example_cli.ExampleCLIKt")
}

dependencies {
    implementation(project(":lib"))
}
