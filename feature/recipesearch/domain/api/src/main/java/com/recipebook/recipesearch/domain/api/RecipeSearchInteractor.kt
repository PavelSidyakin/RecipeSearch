package com.recipebook.recipesearch.domain.api

import com.recipebook.recipesearch.domain.model.RecipesWithPagingInfo

interface RecipeSearchInteractor {
    suspend fun requestRecipes(
        query: String,
        offset: Int,
        number: Int
    ): RecipesWithPagingInfo
}
