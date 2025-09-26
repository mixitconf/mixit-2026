@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

repositories {
    mavenCentral()
}

kotlin {
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "mixit.js"
                scssSupport {
                    enabled = true
                    mode = "extract"
                }
            }
        }
        binaries.executable()
    }
// https://github.com/rjaros/kvision/tree/master

    sourceSets {
        wasmJsMain {
            dependencies {
                implementation(project(":shared"))
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.browser)
                //implementation(libs.konform)
            }
        }
    }

}

tasks.register<Copy>("copy") {
    from(project(":frontend").layout.buildDirectory.dir("dist/wasmJs/productionExecutable"))
    into(project(":backend").layout.buildDirectory.dir("resources/main/static"))
}

tasks.named("wasmJsBrowserDistribution") {
    // Call copy task after wasm compilation to refresh backend resources
    finalizedBy("copy")

}
