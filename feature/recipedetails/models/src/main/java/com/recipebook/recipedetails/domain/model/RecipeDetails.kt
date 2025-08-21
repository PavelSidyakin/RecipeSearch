package com.recipebook.recipedetails.domain.model

/**
 * Model for recipe details.
 */
data class RecipeDetails(
    val recipeId: Int,
    val recipeName: String,
    val recipeImageUrl: String,
    val ingredients: String,
    val instructions: String,
    val sourceWebsiteLink: String,
    val isFavorite: Boolean,
    val price: Float,
)
