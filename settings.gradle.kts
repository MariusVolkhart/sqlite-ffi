include(
	"core",
	"session",
)

pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
	}
}

dependencyResolutionManagement {
	repositories {
		mavenCentral()
		ivy {
			url = uri("https://sqlite.org/2022/")
			patternLayout {
				artifact("[organisation]-[module]-[revision].zip")
			}
			metadataSources { artifact() }
		}
	}
}
