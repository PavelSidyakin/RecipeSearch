package com.recipebook.recipesearch.presentation.viewmodel

internal sealed interface RecipeSearchExternalEvent {
    data class OnRecipeClicked(val recipeId: Int) : RecipeSearchExternalEvent
    data object OnViewedRecipesClicked : RecipeSearchExternalEvent
}
