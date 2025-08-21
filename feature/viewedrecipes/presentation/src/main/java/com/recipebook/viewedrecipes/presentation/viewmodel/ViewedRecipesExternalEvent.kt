package com.recipebook.viewedrecipes.presentation.viewmodel

internal sealed interface ViewedRecipesExternalEvent {
    data class OnRecipeClicked(val recipeId: Int) : ViewedRecipesExternalEvent
    data object OnBackButtonClicked : ViewedRecipesExternalEvent
}
