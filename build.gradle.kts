//apply {
//    from("https://raw.githubusercontent.com/ligi/gradle-common/master/versions_plugin_stable_only.gradle")
//}


//subprojects {
//    repositories {
//        mavenCentral()
//        maven("https://jitpack.io")
//        maven("https://kotlin.bintray.com/kotlinx")
//    }
//
//    tasks.withType<Test> {
//        useJUnitPlatform()
//    }
//
//    configure<JavaPluginExtension> {
//        withSourcesJar()
//        withJavadocJar()
//    }
//
//    afterEvaluate {
//
//        dependencies {
//            "implementation"("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
//
//            "testImplementation"("org.assertj:assertj-core:3.22.0")
//            "testImplementation"("org.junit.jupiter:junit-jupiter-api:${Versions.jupiter}")
//            "testImplementation"("org.junit.jupiter:junit-jupiter-params:${Versions.jupiter}")
//            "testImplementation"("org.junit.jupiter:junit-jupiter-engine:${Versions.jupiter}")
//
//            "testImplementation"("org.jetbrains.kotlin:kotlin-test")
//            "testImplementation"("io.mockk:mockk:1.12.4")
//        }
//
//
//    }
////    val compileTestKotlin by tasks.getting(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class) {
////        kotlinOptions.jvmTarget = "1.8"
////    }
//}
