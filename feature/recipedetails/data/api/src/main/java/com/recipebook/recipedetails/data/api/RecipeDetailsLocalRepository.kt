package com.recipebook.recipedetails.data.api

import com.recipebook.recipedetails.domain.model.RecipeDetails
import kotlinx.coroutines.flow.Flow

interface RecipeDetailsLocalRepository {
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
     * @param recipeId The ID of the recipe.
     *
     * @return RecipeDetails is the recipe is saved in the DB, null otherwise.
     */
    suspend fun requestRecipeDetails(
        recipeId: Int,
    ): RecipeDetails?

    /**
     * Saves recipe in the DB.
     */
    suspend fun saveRecipe(recipeDetails: RecipeDetails)

    /**
     * Set favorite status for a recipe.
     *
     * @param recipeId The ID of the recipe
     * @param isFavorite The new favorite status.
     */
    suspend fun setFavorite(recipeId: Int, isFavorite: Boolean)
}
