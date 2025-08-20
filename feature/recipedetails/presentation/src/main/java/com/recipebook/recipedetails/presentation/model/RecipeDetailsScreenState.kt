package com.recipebook.recipedetails.presentation.model

internal data class RecipeDetailsScreenState(
    val recipeId: Int,
    val recipeName: String,
    val recipeImageUrl: String,
    val ingredients: String,
    val instructions: String,
    val sourceWebsiteLink: String,
    val isFavorite: Boolean,
    val price: Float,
    val errorType: ErrorType?,
) {
    companion object {
        val initialState = RecipeDetailsScreenState(
            recipeId = 0,
            recipeName = "",
            recipeImageUrl = "",
            ingredients = "",
            instructions = "",
            sourceWebsiteLink = "",
            isFavorite = false,
            price = 0f,
            errorType = null,
        )
    }
}
