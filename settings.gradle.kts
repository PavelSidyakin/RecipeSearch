pluginManagement {
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

rootProject.name = "Recipe Search"
include(":app")
include(":feature:mainnavigation:mainactivity")
include(":shared:uikit")
include(":shared:strings")
include(":shared:logging")
include(":feature:mainnavigation:navigationdestination")
include(":feature:recipesearch:recipesearchpresentation")
