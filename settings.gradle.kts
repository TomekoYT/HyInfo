pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()

		maven("https://maven.kikugie.dev/snapshots")
		maven("https://maven.kikugie.dev/releases")

		maven("https://maven.minecraftforge.net/")
		maven("https://maven.fabricmc.net")
		maven("https://maven.ornithemc.net/releases")
		maven("https://maven.ornithemc.net/snapshots")

		maven("https://maven.architectury.dev/")
		maven("https://repo.spongepowered.org/maven/")
		maven("https://repo.essential.gg/repository/maven-public")

		maven("https://repo.polyfrost.org/releases")
		maven("https://repo.polyfrost.org/snapshots")

		maven("https://maven.deftu.dev/releases")
		maven("https://maven.deftu.dev/snapshots")
	}
	resolutionStrategy {
		eachPlugin {
			when (requested.id.id) {
				"gg.essential.loom" -> useModule("gg.essential:architectury-loom:${requested.version}")
			}
		}
	}
}

plugins {
	id("dev.kikugie.stonecutter") version providers.gradleProperty("stonecutter_version")
}

stonecutter {
	create(rootProject) {
		version("1.8.9-forge", "1.8.9").buildscript("build.forge.gradle.kts")
		version("1.8.9-ornithe", "1.8.9").buildscript("build.ornithe.gradle.kts")
		version("26.1-fabric", "26.1")
		version("26.2-fabric", "26.2")
		version("26.3-fabric", "26.3")
		vcsVersion = "26.2-fabric"
	}
}

dependencyResolutionManagement {
	versionCatalogs {
		create("libs")
	}
}

rootProject.name = "HyMod"