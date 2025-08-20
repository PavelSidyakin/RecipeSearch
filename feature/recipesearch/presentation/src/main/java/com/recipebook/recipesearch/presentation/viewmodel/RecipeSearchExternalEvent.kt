package com.recipebook.recipesearch.presentation.viewmodel

sealed interface RecipeSearchExternalEvent {
    data class OnRecipeClicked(val recipeId: Int) : RecipeSearchExternalEvent
    data object OnViewedRecipesClicked : RecipeSearchExternalEvent
}
