package com.recipebook.mainnavigation

import kotlinx.serialization.Serializable

/**
 * Sealed interface that points to the main navigation destinations.
 */
internal sealed interface NavigationDestination {
    /**
     * Recipe search screen.
     */
    @Serializable
    data object RecipeSearch : NavigationDestination

    /**
     * Recipe details screen.
     *
     * @property recipeId The ID of the recipe.
     */
    @Serializable
    data class RecipeDetails(val recipeId: Int) : NavigationDestination

    /**
     * Viewed recipes screen.
     */
    @Serializable
    data object ViewedRecipes : NavigationDestination
}
