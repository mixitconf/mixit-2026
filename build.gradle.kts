allprojects {
	group = "org.mixit"
	version = "1.0.0-SNAPSHOT"
}

plugins {
    alias(libs.plugins.ktlint) apply false
	alias(libs.plugins.kotlin.serialization) apply false
	alias(libs.plugins.kotlin.multiplatform) apply false
	alias(libs.plugins.kotlin.spring) apply false
	alias(libs.plugins.kotlin.jvm) apply false
}