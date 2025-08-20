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
import kotlin.random.Random

internal class RecipeSearchRepositoryImpl @Inject constructor(
    private val spoonacularRemoteDataSource: SpoonacularRemoteDataSource,
) : RecipeSearchRepository {

    override suspend fun requestRecipes(
        query: String,
        offset: Int,
        number: Int,
        sortOption: SearchResultSortOption
    ): RecipesWithPagingInfo {
        return RecipesWithPagingInfo(
            recipes = List(number) { index ->
                RecipeBriefInfo(
                    id = index,
                    imageUrl = "https://img.spoonacular.com/recipes/651715-312x231.jpg",
                    title = "Mexican Three Cheese Dip",
                    summary = "You can never have too many Mexican recipes, so give Mexican Three Cheese Dip a try. For <b>\$3.93 per serving</b>, this recipe",
                    price = Random(index).nextFloat(),
                )
            },
            offset = offset + number,
            number = number,
            totalResults = 0,
        )

        val (sortOption, sortDirection) = sortOption.toSpoonacularRecipeSortOptionAndSpoonacularRecipeSortDirection()

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

    private fun SearchResultSortOption.toSpoonacularRecipeSortOptionAndSpoonacularRecipeSortDirection(

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
