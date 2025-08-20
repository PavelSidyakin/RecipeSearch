package com.recipebook.viewedrecipes.domain.impl

import com.recipebook.viewedrecipes.data.api.ViewedRecipesRepository
import com.recipebook.viewedrecipes.domain.api.ViewedRecipesInteractor
import com.recipebook.viewedrecipes.domain.model.ViewedRecipeDetails
import javax.inject.Inject

internal class ViewedRecipesInteractorImpl @Inject constructor(
    private val viewedRecipesRepository: ViewedRecipesRepository,
) : ViewedRecipesInteractor {
    override suspend fun requestViewedRecipes(): List<ViewedRecipeDetails> {
        return viewedRecipesRepository.requestViewedRecipes()
    }

    override suspend fun requestFavoriteViewedRecipes(): List<ViewedRecipeDetails> {
        return viewedRecipesRepository.requestFavoriteViewedRecipes()
    }
}
