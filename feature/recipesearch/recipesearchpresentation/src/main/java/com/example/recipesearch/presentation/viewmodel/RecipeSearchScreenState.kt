package com.example.recipesearch.presentation.viewmodel

internal data class RecipeSearchScreenState(
    val text: String,
) {
    companion object {
        val initialState = RecipeSearchScreenState(
            text = "",
        )
    }
}
