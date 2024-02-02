plugins {
	`kotlin-dsl`
}

gradlePlugin {
	plugins {
		register("sqlite-extract") {
			id = "sqlite-extract"
			implementationClass = "com.volkhart.gradle.SqliteExtractPlugin"
		}
		register("sqlite-publish") {
			id = "sqlite-publish"
			implementationClass = "com.volkhart.gradle.SqlitePublishPlugin"
		}
	}
}

repositories {
	mavenCentral()
	gradlePluginPortal()
}

dependencies {
	implementation("gradle.plugin.io.github.krakowski:gradle-jextract:0.4.1")
}
