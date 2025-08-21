package com.recipebook.recipedetails.domain.api

import com.recipebook.recipedetails.domain.model.RecipeDetails
import kotlinx.coroutines.flow.Flow

interface RecipeDetailsInteractor {
    /**
     * Observes RecipeDetails.
     * Emits if the recipe details has been updated in the DB.
     *
     * @param recipeId The ID of the recipe.
     * @return [Flow] of RecipeDetails
     */
    fun observeRecipeDetails(recipeId: Int): Flow<RecipeDetails>

    /**
     * Returns RecipeDetails.
     * Checks the DB first, if found - returns the details from the DB.
     * If not found requests the recipe details from the network and saves to the DB.
     *
     * @param recipeId The ID of the recipe.
     *
     * @return RecipeDetails.
     *
     * @throws java.io.IOException in case of a network failure
     * @throws RuntimeException in other failures
     */
    suspend fun requestRecipeDetails(recipeId: Int): RecipeDetails

    /**
     * Set favorite status for a recipe.
     *
     * @param recipeId The ID of the recipe
     * @param isFavorite The new favorite status.
     */
    suspend fun setFavorite(recipeId: Int, isFavorite: Boolean)
}
