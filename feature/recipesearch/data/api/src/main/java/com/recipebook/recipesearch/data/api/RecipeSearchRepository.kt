package com.recipebook.recipesearch.data.api

import com.recipebook.recipesearch.domain.model.RecipesWithPagingInfo
import com.recipebook.recipesearch.domain.model.SearchResultSortOption

interface RecipeSearchRepository {
    /**
     * Request the recipes with provided search criteria and paging data.
     *
     * @param query A string query, represents recipe name or ingredients.
     * @param offset The item offset.
     * @param number The number of items to return.
     * @param sortOption The [SearchResultSortOption] - sort option.
     */
    suspend fun requestRecipes(
        query: String,
        offset: Int,
        number: Int,
        sortOption: SearchResultSortOption,
    ): RecipesWithPagingInfo
}
