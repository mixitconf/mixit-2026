plugins {
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
    // implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation(libs.spring.boot.starter.web)
	implementation(libs.kotlinx.serialization.json)
	implementation(libs.kotlinx.datetime)
	implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.markdown)
	implementation(project(":shared"))

    // TODO
    implementation("org.webjars:bootstrap:5.3.7")
    implementation("org.webjars:webjars-locator:0.30")

	// developmentOnly(libs.spring.boot.docker.compose)

	testImplementation(libs.spring.boot.starter.test)
	testImplementation(libs.kotlin.test)

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
		from(project(":frontend").layout.buildDirectory
			.dir("dist/wasmJs/productionExecutable")) {
			into("static")
		}
	}
}