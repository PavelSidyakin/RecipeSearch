package com.recipebook.recipedetails.domain.api

import com.recipebook.recipedetails.domain.model.RecipeDetails
import kotlinx.coroutines.flow.Flow

interface RecipeDetailsInteractor {
    fun observeRecipeDetails(recipeId: Int): Flow<RecipeDetails>
    suspend fun requestRecipeDetails(recipeId: Int): RecipeDetails
    suspend fun setFavorite(recipeId: Int, isFavorite: Boolean)
}
