package com.volkhart.gradle

import io.github.krakowski.jextract.JextractTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

class SqliteExtractPlugin : Plugin<Project> {
	override fun apply(target: Project): Unit = target.run {
		pluginManager.apply("io.github.krakowski.jextract")

		if (plugins.hasPlugin(JavaPlugin::class.java)) {
			// Download SQLite sources
			val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
			dependencies {
				add("compileOnly", libs.findLibrary("sqliteSource").orElseThrow())
			}

			val sqliteExtractDirectory = buildDir.resolve("sqlite")
			extra[SQLITE_HEADER] = sqliteExtractDirectory.resolve(SQLITE_HEADER_NAME).path

			val extractSqliteHeader = tasks.register<Copy>("extractSqliteHeader") {
				val dependency = configurations.named("compileClasspath").get().files { it.group == "sqlite" }.first()
				check(dependency.exists()) { "sqlite dependency not found" }

				val zip = zipTree(dependency)
				from(zip.files) { include { it.name == SQLITE_HEADER_NAME } }
				into(sqliteExtractDirectory)
			}

			tasks.withType<JextractTask> {
				dependsOn(extractSqliteHeader)
			}
		}
	}

	companion object {
		const val SQLITE_HEADER = "sqlite_header_location"
		private const val SQLITE_HEADER_NAME = "sqlite3.h"
	}
}
