package com.recipebook.viewedrecipes.domain.model

/**
 * Viewed recipe details.
 */
data class ViewedRecipeDetails(
    val recipeId: Int,
    val recipeName: String,
    val recipeImageUrl: String,
    val description: String,
    val isFavorite: Boolean,
    val price: Float,
)
