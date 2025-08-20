package com.recipebook.recipesearch.presentation.model

internal data class RecipeSearchListItemState(
    val recipeId: Int,
    val imageUrl: String,
    val name: String,
    val description: String,
    val price: Float,
)
