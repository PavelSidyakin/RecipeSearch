package com.recipebook.recipesearch.data.impl

import com.recipebook.datasource.remote.SpoonacularRemoteDataSource
import com.recipebook.datasource.remote.model.SpoonacularRecipeBriefInfo
import com.recipebook.datasource.remote.model.SpoonacularRecipeResponse
import com.recipebook.datasource.remote.model.SpoonacularRecipeSortDirection
import com.recipebook.datasource.remote.model.SpoonacularRecipeSortOption
import com.recipebook.recipesearch.data.api.RecipeSearchRepository
import com.recipebook.recipesearch.domain.model.RecipeBriefInfo
import com.recipebook.recipesearch.domain.model.RecipesWithPagingInfo
import com.recipebook.recipesearch.domain.model.SearchResultSortOption
import javax.inject.Inject

internal class RecipeSearchRepositoryImpl @Inject constructor(
    private val spoonacularRemoteDataSource: SpoonacularRemoteDataSource,
) : RecipeSearchRepository {

    override suspend fun requestRecipes(
        query: String,
        offset: Int,
        number: Int,
        sortOption: SearchResultSortOption
    ): RecipesWithPagingInfo {
        val (sortOption, sortDirection) = sortOption.toSpoonacularRecipeSortOptionAndSortDirection()

        return spoonacularRemoteDataSource.requestRecipes(
            query = query,
            offset = offset,
            number = number,
            sortOption = sortOption,
            sortDirection = sortDirection,
        ).toRecipesWithPagingInfo()
    }

    private fun SpoonacularRecipeResponse.toRecipesWithPagingInfo(): RecipesWithPagingInfo {
        return RecipesWithPagingInfo(
            recipes = recipes.map { it.toRecipeBriefInfo() },
            offset = offset,
            number = number,
            totalResults = totalResults,
        )
    }

    private fun SpoonacularRecipeBriefInfo.toRecipeBriefInfo(): RecipeBriefInfo {
        return RecipeBriefInfo(
            id = id,
            imageUrl = imageUrl,
            title = title,
            summary = summary,
            price = pricePerServing,
        )
    }

    private fun SearchResultSortOption.toSpoonacularRecipeSortOptionAndSortDirection(
    ): Pair<SpoonacularRecipeSortOption, SpoonacularRecipeSortDirection> {
        return when (this) {
            SearchResultSortOption.CALORIES_ASCENDING -> SpoonacularRecipeSortOption.CALORIES to
                    SpoonacularRecipeSortDirection.ASCENDING

            SearchResultSortOption.CALORIES_DESCENDING -> SpoonacularRecipeSortOption.CALORIES to
                    SpoonacularRecipeSortDirection.DESCENDING

            SearchResultSortOption.PRICE_ASCENDING -> SpoonacularRecipeSortOption.PRICE to
                    SpoonacularRecipeSortDirection.ASCENDING

            SearchResultSortOption.PRICE_DESCENDING -> SpoonacularRecipeSortOption.PRICE to
                    SpoonacularRecipeSortDirection.DESCENDING
        }
    }
}
