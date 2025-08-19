package com.recipebook.mainnavigation

import kotlinx.serialization.Serializable

internal sealed interface NavigationDestination {
    @Serializable
    data object RecipeSearch : NavigationDestination

    @Serializable
    data class RecipeDetails(val recipeId: Int) : NavigationDestination
}
