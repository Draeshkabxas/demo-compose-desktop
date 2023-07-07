import org.jetbrains.compose.desktop.application.dsl.TargetFormat


val koin_core_version: String by project
val koin_androidx_compose_version: String by project
val realm_version: String by project
val kotlin_coroutines_version: String by project

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("io.realm.kotlin")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
}


kotlin {
    jvm {
        jvmToolchain(15)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {    val nav_version = "2.6.0"

                implementation("androidx.navigation:navigation-compose:$nav_version")
                implementation(compose.desktop.currentOs)
                implementation(compose.materialIconsExtended)
                //Koin Dependency injection
                implementation("io.insert-koin:koin-core:$koin_core_version")
                implementation("io.insert-koin:koin-androidx-compose:$koin_androidx_compose_version")

                //Apache POI library to work with Xlsx files
                implementation("org.apache.poi:poi:4.1.2")
                implementation("org.apache.poi:poi-ooxml:4.1.2")

                //Realm Mongodb Database
                implementation("io.realm.kotlin:library-base:$realm_version") // Add to use local realm (no sync)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version") // Add to use coroutines with the SDK


                val lwjglVersion = "3.3.1"
                listOf("lwjgl", "lwjgl-nfd").forEach { lwjglDep ->
                    implementation("org.lwjgl:${lwjglDep}:${lwjglVersion}")
                    listOf(
                        "natives-windows", "natives-windows-x86", "natives-windows-arm64",
                        "natives-macos", "natives-macos-arm64",
                        "natives-linux", "natives-linux-arm64", "natives-linux-arm32"
                    ).forEach { native ->
                        runtimeOnly("org.lwjgl:${lwjglDep}:${lwjglVersion}:${native}")
                    }
                }
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "demo"
            packageVersion = "1.0.0"
        }
    }
}
