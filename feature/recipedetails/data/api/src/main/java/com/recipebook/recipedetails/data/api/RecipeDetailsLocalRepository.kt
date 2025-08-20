package com.recipebook.recipedetails.data.api

import com.recipebook.recipedetails.domain.model.RecipeDetails
import kotlinx.coroutines.flow.Flow

interface RecipeDetailsLocalRepository {
    fun observeRecipeDetails(recipeId: Int): Flow<RecipeDetails>
    suspend fun requestRecipeDetails(
        recipeId: Int,
    ): RecipeDetails?
    suspend fun saveRecipe(recipeDetails: RecipeDetails)
    suspend fun setFavorite(recipeId: Int, isFavorite: Boolean)
}
