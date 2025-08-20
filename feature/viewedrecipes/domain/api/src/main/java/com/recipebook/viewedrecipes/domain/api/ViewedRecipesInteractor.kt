package com.recipebook.viewedrecipes.domain.api

import com.recipebook.viewedrecipes.domain.model.ViewedRecipeDetails

interface ViewedRecipesInteractor {
    suspend fun requestViewedRecipes(): List<ViewedRecipeDetails>
    suspend fun requestFavoriteViewedRecipes(): List<ViewedRecipeDetails>
}
