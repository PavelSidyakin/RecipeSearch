package com.recipebook.recipedetails.presentation.viewmodel

internal sealed interface RecipeDetailsExternalEvent {
    data object OnBackButtonClicked : RecipeDetailsExternalEvent
}
