package com.volkhart.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get

class SqlitePublishPlugin : Plugin<Project> {
	override fun apply(target: Project) = target.run {
		apply<MavenPublishPlugin>()

		configure<JavaPluginExtension> {
			withSourcesJar()
		}

		configure<PublishingExtension> {
			publications {
				register("mavenJava", MavenPublication::class.java) {
					artifactId = pomArtifactId
					groupId = "com.volkhart.sqlite-ffi"
					from(components["java"])
					pom {
						name.set(pomName)
						packaging = pomPackaging
						description.set(pomDescription)
						licenses {
							license {
								name.set("The Apache License, Version 2.0")
								url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
							}
						}
						scm {
							connection.set("scm:git:git://example.com/my-library.git")
							developerConnection.set("scm:git:ssh://example.com/my-library.git")
							url.set("https://example.com/my-library/")
						}
					}
				}
			}
			repositories {
				maven {
					url = uri(if (version.toString().isReleaseBuild) releaseRepositoryUrl else snapshotRepositoryUrl)
					credentials {
						username = repositoryUsername
						password = repositoryPassword
					}
				}
			}
		}
	}
}

val String.isReleaseBuild
	get() = !contains("SNAPSHOT")

val Project.releaseRepositoryUrl: String
	get() = properties.getOrDefault(
		"RELEASE_REPOSITORY_URL",
		"https://oss.sonatype.org/service/local/staging/deploy/maven2/"
	).toString()

val Project.snapshotRepositoryUrl: String
	get() = properties.getOrDefault(
		"SNAPSHOT_REPOSITORY_URL",
		"https://oss.sonatype.org/content/repositories/snapshots/"
	).toString()

val Project.repositoryUsername: String
	get() = properties.getOrDefault("SONATYPE_NEXUS_USERNAME", "").toString()

val Project.repositoryPassword: String
	get() = properties.getOrDefault("SONATYPE_NEXUS_PASSWORD", "").toString()

val Project.pomPackaging: String
	get() = properties.getOrDefault("POM_PACKAGING", "jar").toString()

val Project.pomName: String?
	get() = properties["POM_NAME"]?.toString()

val Project.pomDescription: String?
	get() = properties["POM_DESCRIPTION"]?.toString()

val Project.pomArtifactId
	get() = properties.getOrDefault("POM_ARTIFACT_ID", name).toString()
