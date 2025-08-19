package com.recipebook.recipedetails.data.api

import com.recipebook.recipedetails.domain.model.RecipeDetails

interface RecipeDetailsLocalRepository {
    suspend fun requestRecipeDetails(
        recipeId: Int,
    ): RecipeDetails?
    suspend fun saveRecipe(recipeDetails: RecipeDetails)
}
