package com.recipebook.recipedetails.presentation.viewmodel

sealed interface RecipeDetailsExternalEvent {
    data object OnBackButtonClicked : RecipeDetailsExternalEvent
}
