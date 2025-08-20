package com.recipebook.viewedrecipes.domain.model

data class ViewedRecipeDetails(
    val recipeId: Int,
    val recipeName: String,
    val recipeImageUrl: String,
    val description: String,
    val isFavorite: Boolean,
    val price: Float,
)
