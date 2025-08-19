package com.recipebook.recipesearch.data.api

import com.recipebook.recipesearch.domain.model.RecipesWithPagingInfo
import com.recipebook.recipesearch.domain.model.SearchResultSortOption

interface RecipeSearchRepository {
    suspend fun requestRecipes(
        query: String,
        offset: Int,
        number: Int,
        sortOption: SearchResultSortOption,
    ): RecipesWithPagingInfo
}
