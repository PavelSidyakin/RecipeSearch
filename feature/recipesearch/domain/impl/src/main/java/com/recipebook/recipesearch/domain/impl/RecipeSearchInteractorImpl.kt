package com.recipebook.recipesearch.domain.impl

import com.recipebook.logging.debugLog
import com.recipebook.recipesearch.data.api.RecipeSearchRepository
import com.recipebook.recipesearch.domain.api.RecipeSearchInteractor
import com.recipebook.recipesearch.domain.model.RecipesWithPagingInfo
import javax.inject.Inject

private const val LOG_TAG = "RecipeSearchInteractor"

internal class RecipeSearchInteractorImpl @Inject constructor(
    private val recipeSearchRepository: RecipeSearchRepository,
) : RecipeSearchInteractor {
    override suspend fun requestRecipes(
        query: String,
        offset: Int,
        number: Int,
    ): RecipesWithPagingInfo {
        debugLog { tag = LOG_TAG; message = "requestRecipes() query=$query, offset=$offset, number=$number" }
        return recipeSearchRepository.requestRecipes(query, offset, number).also { result ->
            debugLog { tag = LOG_TAG; message = "requestRecipes() result=$result" }
        }
    }
}
