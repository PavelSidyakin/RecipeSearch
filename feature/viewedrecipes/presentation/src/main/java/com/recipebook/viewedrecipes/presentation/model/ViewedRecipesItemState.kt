package com.recipebook.viewedrecipes.presentation.model

internal data class ViewedRecipesItemState(
    val recipeId: Int,
    val imageUrl: String,
    val name: String,
    val description: String,
    val price: Float,
    val isFavorite: Boolean,
)
