package com.recipebook.recipedetails.data.api

import com.recipebook.recipedetails.domain.model.RecipeDetails

interface RecipeDetailsRemoteRepository {
    suspend fun requestRecipeDetails(
        recipeId: Int,
    ): RecipeDetails
}
