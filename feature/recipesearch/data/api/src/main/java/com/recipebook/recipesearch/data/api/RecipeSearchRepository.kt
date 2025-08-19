package com.recipebook.recipesearch.data.api

import com.recipebook.recipesearch.domain.model.RecipesWithPagingInfo

interface RecipeSearchRepository {
    suspend fun requestRecipes(
        query: String,
        offset: Int,
        number: Int,
    ): RecipesWithPagingInfo
}
