package com.recipebook.viewedrecipes.presentation.viewmodel

sealed interface ViewedRecipesExternalEvent {
    data class OnRecipeClicked(val recipeId: Int) : ViewedRecipesExternalEvent
}
