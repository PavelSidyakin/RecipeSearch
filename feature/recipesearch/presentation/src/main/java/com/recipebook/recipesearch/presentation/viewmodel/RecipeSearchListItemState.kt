package com.recipebook.recipesearch.presentation.viewmodel

internal data class RecipeSearchListItemState(
    val recipeId: Int,
    val imageUrl: String,
    val name: String,
    val description: String,
)
