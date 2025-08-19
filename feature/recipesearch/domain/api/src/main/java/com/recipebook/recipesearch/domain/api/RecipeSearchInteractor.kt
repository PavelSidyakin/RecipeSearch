package com.recipebook.recipesearch.domain.api

import com.recipebook.recipesearch.domain.model.RecipesWithPagingInfo
import com.recipebook.recipesearch.domain.model.SearchResultSortOption

interface RecipeSearchInteractor {
    suspend fun requestRecipes(
        query: String,
        offset: Int,
        number: Int,
        sortOption: SearchResultSortOption,
    ): RecipesWithPagingInfo
}
