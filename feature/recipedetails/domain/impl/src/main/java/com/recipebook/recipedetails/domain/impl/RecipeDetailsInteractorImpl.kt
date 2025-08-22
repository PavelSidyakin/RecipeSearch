package com.recipebook.recipedetails.domain.impl

import com.recipebook.logging.debugLog
import com.recipebook.recipedetails.data.api.RecipeDetailsLocalRepository
import com.recipebook.recipedetails.data.api.RecipeDetailsRemoteRepository
import com.recipebook.recipedetails.domain.api.RecipeDetailsInteractor
import com.recipebook.recipedetails.domain.model.RecipeDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val LOG_TAG = "RecipeDetailsInteractor"

internal class RecipeDetailsInteractorImpl @Inject constructor(
    private val recipeDetailsLocalRepository: RecipeDetailsLocalRepository,
    private val recipeDetailsRemoteRepository: RecipeDetailsRemoteRepository,
) : RecipeDetailsInteractor {
    override fun observeRecipeDetails(recipeId: Int): Flow<RecipeDetails> {
        return recipeDetailsLocalRepository.observeRecipeDetails(recipeId)
    }

    override suspend fun requestRecipeDetails(recipeId: Int): RecipeDetails {
        debugLog { tag = LOG_TAG; message = "requestRecipeDetails() recipeId=$recipeId" }
        val localRecipe = recipeDetailsLocalRepository.requestRecipeDetails(recipeId)
        debugLog { tag = LOG_TAG; message = "requestRecipeDetails() localRecipe=$localRecipe" }

        return recipeDetailsLocalRepository.requestRecipeDetails(recipeId) ?: requestInRemoteAndSave(recipeId)
    }

    override suspend fun setFavorite(recipeId: Int, isFavorite: Boolean) {
        recipeDetailsLocalRepository.setFavorite(recipeId, isFavorite)
    }

    private suspend fun requestInRemoteAndSave(recipeId: Int): RecipeDetails {
        debugLog { tag = LOG_TAG; message = "requestInRemoteAndSave() recipeId=$recipeId" }
        return recipeDetailsRemoteRepository.requestRecipeDetails(recipeId).also { recipeDetails ->
            recipeDetailsLocalRepository.saveRecipe(recipeDetails)
        }
    }
}
