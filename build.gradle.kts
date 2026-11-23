import net.labymod.labygradle.common.internal.labymod.addon.model.AddonPermission

plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "com.magnus.mintmc"
version = providers.environmentVariable("VERSION").getOrElse("1.0.0")

labyMod {
    defaultPackageName = "com.magnus.mintmc"

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    // When the property is set to true, you can log in with a Minecraft account
                    // devLogin = true
                }
            }
        }
    }

    addonInfo {
        namespace = "mintmc"
        displayName = "headtool"
        author = "Magnus"
        description = "Shows whose player head you are looking at and copies its texture with a keybind."
        minecraftVersion = "*"
        version = rootProject.version.toString()
        permission(AddonPermission.FILE_SYSTEM, AddonPermission.EXTERNAL_SERVERS_GET)
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")

    group = rootProject.group
    version = rootProject.version

    extensions.findByType(JavaPluginExtension::class.java)?.apply {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}