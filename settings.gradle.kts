pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BBank"
include(":app")
include(":core")
include(":core:presentation")
include(":core:presentation:ui")
include(":core:domain")
include(":core:data")
include(":core:database")
include(":news")
include(":news:data")
include(":news:network")
include(":news:presentation")
include(":department")
include(":department:network")
include(":department:presentation")
include(":converter")
include(":converter:presentation")