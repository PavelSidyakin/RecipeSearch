package com.recipebook.recipedetails.data.impl

import com.recipebook.datasource.remote.SpoonacularRemoteDataSource
import com.recipebook.datasource.remote.model.SpoonacularRecipeInformationResponse
import com.recipebook.recipedetails.data.api.RecipeDetailsRemoteRepository
import com.recipebook.recipedetails.domain.model.RecipeDetails
import javax.inject.Inject

internal class RecipeDetailsRemoteRepositoryImpl @Inject constructor(
    private val spoonacularRemoteDataSource: SpoonacularRemoteDataSource,
) : RecipeDetailsRemoteRepository {
    override suspend fun requestRecipeDetails(recipeId: Int): RecipeDetails {
        return spoonacularRemoteDataSource.requestRecipeInformation(recipeId).toRecipeDetails()
    }

    private fun SpoonacularRecipeInformationResponse.toRecipeDetails(): RecipeDetails {
        return RecipeDetails(
            recipeId = id,
            recipeName = title,
            recipeImageUrl = imageUrl,
            ingredients = ingredients.joinToString("\n") { it.ingredientDescription },
            instructions = summary,
            sourceWebsiteLink = sourceWebsiteLink,
            isFavorite = false,
            price = pricePerServing,
        )
    }
}
