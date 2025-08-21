package com.recipebook.viewedrecipes.domain.api

import com.recipebook.viewedrecipes.domain.model.ViewedRecipeDetails

interface ViewedRecipesInteractor {
    /**
     * Requests viewed recipes from the DB.
     *
     * @return List of [ViewedRecipeDetails].
     */
    suspend fun requestViewedRecipes(): List<ViewedRecipeDetails>

    /**
     * Requests viewed favorite recipes from the DB.
     *
     * @return List of [ViewedRecipeDetails].
     */
    suspend fun requestFavoriteViewedRecipes(): List<ViewedRecipeDetails>
}
