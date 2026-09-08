import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val modName = project.property("mod_name") as String
val modId = project.property("mod_id") as String
val modVersion = project.property("mod_version") as String
val modDescription = project.property("mod_description") as String
val modArchivesName = project.property("mod_archives_name") as String
val baseGroup = project.property("base_group") as String

val javaVersion = project.property("java_version") as String
val minecraftVersion = project.property("minecraft_version") as String

val fabricLoaderVersion = project.property("fabric_loader_version") as String

val oslVersion = project.property("osl_version") as String

val oneconfigVersion = project.property("oneconfig_version") as String
val modMenuVersion = project.property("mod_menu_version") as String

repositories {
    mavenCentral()
    google()

    maven("https://maven.cloverclient.com/releases")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.stellardrift.ca/repository/maven-snapshots/")
    maven("https://repo.polyfrost.org/releases")
    maven("https://repo.polyfrost.org/snapshots")
}

plugins {
    id("net.fabricmc.fabric-loom-remap") version "1.17-SNAPSHOT"
    id("ploceus") version "1.17-SNAPSHOT"
    id("org.jetbrains.kotlin.jvm") version "2.4.10"
    id("dev.deftu.gradle.bloom") version "0.2.0"
}

ploceus {
    setIntermediaryGeneration(2)
}

base {
    archivesName.set("$modArchivesName-$modVersion-${minecraftVersion}_ornithe")
}

loom {
    runConfigs.remove(runConfigs["server"])
}

dependencies {
    implementation(kotlin("stdlib"))
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(ploceus.mcpMappings("stable", "1.8.9", "22"))
    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    ploceus.dependOsl(oslVersion)

    modImplementation("org.polyfrost.oneconfig:$minecraftVersion-ornithe:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:commands:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:config:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:config-impl:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:events:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:internal:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:ui:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:utils:$oneconfigVersion")
    implementation("org.polyfrost.oneconfig:hud:$oneconfigVersion")

    modImplementation("com.terraformersmc:modmenu:$modMenuVersion+mc$minecraftVersion")
}

bloom {
    replacement("@MOD_NAME@", modName)
    replacement("@MOD_ID@", modId)
    replacement("@MOD_VERSION@", modVersion)
}

tasks.processResources {
    val props = mapOf(
        "mod_id" to modId,
        "mod_name" to modName,
        "mod_version" to modVersion,
        "mod_description" to modDescription,
        "mod_archives_name" to modArchivesName,
        "base_group" to baseGroup,

        "java_version" to javaVersion,
        "minecraft_version" to minecraftVersion,
        "fabric_loader_version" to fabricLoaderVersion,
        "osl_version" to oslVersion,

        "oneconfig_version" to oneconfigVersion,
        "mod_menu_version" to modMenuVersion,
    )

    inputs.properties(props)

    filesMatching(listOf("fabric.mod.json", "mixins.$modId.json")) {
        expand(props)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = javaVersion.toInt()
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.toVersion(javaVersion)
    targetCompatibility = JavaVersion.toVersion(javaVersion)

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.fromTarget(javaVersion)
    }
}

tasks.jar {
    inputs.property("archivesName", base.archivesName)

    from("LICENSE") {
        rename { "${it}_${base.archivesName.get()}" }
    }
}