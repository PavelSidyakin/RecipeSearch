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
include(":feature:recipesearch:presentation")
include(":feature:recipesearch:domain:api")
include(":feature:recipesearch:domain:impl")
include(":datasource:remote")
include(":feature:recipesearch:data:api")
include(":feature:recipesearch:data:impl")
include(":feature:recipesearch:domain:models")
