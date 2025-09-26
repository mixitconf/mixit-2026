import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
	jvm()

	@OptIn(ExperimentalWasmDsl::class)
	wasmJs {
		browser {
			val rootDirPath = project.rootDir.path
			val projectDirPath = project.projectDir.path
			commonWebpackConfig {
				devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
					static = (static ?: mutableListOf()).apply {
						// Serve sources to debug inside browser
						add(rootDirPath)
						add(projectDirPath)
					}
				}
				cssSupport { enabled.set(true) }
				scssSupport { enabled.set(true) }
			}
		}
	}

	sourceSets {
		commonMain.dependencies {
			implementation(kotlin("stdlib-common"))
			implementation(libs.kotlinx.datetime)
			implementation(libs.kotlinx.html.common)
			implementation(libs.kotlinx.serialization.json)
		}
	}
}
