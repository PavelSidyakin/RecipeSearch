package com.recipebook.recipedetails.domain.api

import com.recipebook.recipedetails.domain.model.RecipeDetails

interface RecipeDetailsInteractor {
    suspend fun requestRecipeDetails(recipeId: Int): RecipeDetails
}
