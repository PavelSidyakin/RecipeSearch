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
include(":feature:recipesearch:data:api")
include(":feature:recipesearch:data:impl")
include(":feature:recipesearch:models")
include(":datasource:local")
include(":datasource:remote")
include(":feature:recipedetails:presentation")
include(":feature:recipedetails:domain:api")
include(":feature:recipedetails:domain:impl")
include(":feature:recipedetails:data:api")
include(":feature:recipedetails:data:impl")
include(":feature:recipedetails:models")
include(":feature:viewedrecipes:presentation")
include(":feature:viewedrecipes:domain:api")
include(":feature:viewedrecipes:domain:impl")
include(":feature:viewedrecipes:data:api")
include(":feature:viewedrecipes:data:impl")
include(":feature:viewedrecipes:models")
