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
}


kotlin {
    jvm {
        jvmToolchain(17)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.materialIconsExtended)
                //Koin Dependency injection
                implementation("io.insert-koin:koin-core:$koin_core_version")
                implementation("io.insert-koin:koin-androidx-compose:$koin_androidx_compose_version")

                //Realm Mongodb Database
                implementation("io.realm.kotlin:library-base:$realm_version") // Add to use local realm (no sync)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version") // Add to use coroutines with the SDK
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
