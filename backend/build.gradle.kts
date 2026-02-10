plugins {
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.cache)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.serialization)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.markdown)
    implementation(libs.webjars.bootstrap)
    implementation(libs.webjars.locator)
    implementation(project(":shared"))

    implementation(libs.json.web.token)
    implementation(libs.json.web.token.jackson)

    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockk)

    testRuntimeOnly(libs.junit.launcher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    processResources {
        dependsOn(":frontend:wasmJsBrowserDistribution", ":frontend:copy")
        from(
            project(":frontend")
                .layout.buildDirectory
                .dir("dist/wasmJs/productionExecutable"),
        ) {
            into("static")
        }
    }
}
