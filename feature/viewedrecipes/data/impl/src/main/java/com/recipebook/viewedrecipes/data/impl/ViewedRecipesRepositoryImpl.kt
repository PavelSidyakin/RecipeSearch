package com.recipebook.viewedrecipes.data.impl

import com.recipebook.datasource.local.db.dao.ViewedRecipeDao
import com.recipebook.datasource.local.db.entity.ViewedRecipeEntity
import com.recipebook.utils.coroutineutils.DispatcherProvider
import com.recipebook.viewedrecipes.data.api.ViewedRecipesRepository
import com.recipebook.viewedrecipes.domain.model.ViewedRecipeDetails
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ViewedRecipesRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val viewedRecipeDao: ViewedRecipeDao,
) : ViewedRecipesRepository {
    override suspend fun requestViewedRecipes(): List<ViewedRecipeDetails> {
        return withContext(dispatcherProvider.io()) {
            viewedRecipeDao.requestViewedRecipes().map { it.toViewedRecipeDetails() }
        }
    }

    override suspend fun requestFavoriteViewedRecipes(
    ): List<ViewedRecipeDetails> {
        return withContext(dispatcherProvider.io()) {
            viewedRecipeDao.requestFavoriteViewedRecipes().map { it.toViewedRecipeDetails() }
        }
    }

    private fun ViewedRecipeEntity.toViewedRecipeDetails(): ViewedRecipeDetails {
        return ViewedRecipeDetails(
            recipeId = recipeId,
            recipeName = recipeName,
            recipeImageUrl = recipeImageUrl,
            description = instructions,
            isFavorite = isFavorite,
            price = price,
        )
    }
}
