package com.recipebook.recipesearch.data.impl

import com.recipebook.datasource.remote.SpoonacularRemoteDataSource
import com.recipebook.datasource.remote.model.SpoonacularRecipeBriefInfo
import com.recipebook.datasource.remote.model.SpoonacularRecipeResponse
import com.recipebook.recipesearch.data.api.RecipeSearchRepository
import com.recipebook.recipesearch.domain.model.RecipeBriefInfo
import com.recipebook.recipesearch.domain.model.RecipesWithPagingInfo
import javax.inject.Inject

internal class RecipeSearchRepositoryImpl @Inject constructor(
    private val spoonacularRemoteDataSource: SpoonacularRemoteDataSource,
) : RecipeSearchRepository {
    override suspend fun requestRecipes(
        query: String,
        offset: Int,
        number: Int
    ): RecipesWithPagingInfo {
        return RecipesWithPagingInfo(
            recipes = List(number) { index ->
                RecipeBriefInfo(
                    id = index,
                    imageUrl = "https://img.spoonacular.com/recipes/651715-312x231.jpg",
                    title = "Mexican Three Cheese Dip",
                    summary = "You can never have too many Mexican recipes, so give Mexican Three Cheese Dip a try. For <b>\$3.93 per serving</b>, this recipe",
                )
            },
            offset = offset + number,
            number = number,
            totalResults = 0,
        )


//        return spoonacularRemoteDataSource.requestRecipes(
//            query = query,
//            offset = offset,
//            number = number
//        ).toRecipesWithPagingInfo()
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
        )
    }
}
