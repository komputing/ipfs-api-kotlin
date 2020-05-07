plugins {
    application
}

application {
    mainClassName = "org.kethereum.example_cli.ExampleCLIKt"
}

dependencies {
    implementation(project(":lib"))
}
