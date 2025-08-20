package com.recipebook.viewedrecipes.data.api

import com.recipebook.viewedrecipes.domain.model.ViewedRecipeDetails

interface ViewedRecipesRepository {
    suspend fun requestViewedRecipes(): List<ViewedRecipeDetails>
    suspend fun requestFavoriteViewedRecipes(): List<ViewedRecipeDetails>
}
