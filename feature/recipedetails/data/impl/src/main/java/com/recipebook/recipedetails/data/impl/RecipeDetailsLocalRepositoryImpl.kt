package com.recipebook.recipedetails.data.impl

import com.recipebook.datasource.local.db.dao.ViewedRecipeDao
import com.recipebook.datasource.local.db.entity.ViewedRecipeEntity
import com.recipebook.recipedetails.data.api.RecipeDetailsLocalRepository
import com.recipebook.recipedetails.domain.model.RecipeDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class RecipeDetailsLocalRepositoryImpl @Inject constructor(
    private val viewedRecipeDao: ViewedRecipeDao,
) : RecipeDetailsLocalRepository {
    override suspend fun requestRecipeDetails(recipeId: Int): RecipeDetails? = withContext(Dispatchers.IO) {
        viewedRecipeDao.requestViewedRecipe(recipeId = recipeId)?.toRecipeDetails()
    }

    override suspend fun saveRecipe(recipeDetails: RecipeDetails) = withContext(Dispatchers.IO) {
        viewedRecipeDao.updateOrInsertViewedRecipe(recipeDetails.toViewedRecipeEntity())
    }

    private fun ViewedRecipeEntity.toRecipeDetails(): RecipeDetails {
        return RecipeDetails(
            recipeId = recipeId,
            recipeName = recipeName,
            recipeImageUrl = recipeImageUrl,
            ingredients = ingredients,
            instructions = instructions,
            sourceWebsiteLink = sourceWebsiteLink,
            isFavorite = isFavorite,
            price = price,
        )
    }

    private fun RecipeDetails.toViewedRecipeEntity(): ViewedRecipeEntity {
        return ViewedRecipeEntity(
            recipeId = recipeId,
            recipeName = recipeName,
            recipeImageUrl = recipeImageUrl,
            ingredients = ingredients,
            instructions = instructions,
            sourceWebsiteLink = sourceWebsiteLink,
            isFavorite = isFavorite,
            price = price,
        )
    }
}
