package com.recipebook.recipedetails.data.api

import com.recipebook.recipedetails.domain.model.RecipeDetails

interface RecipeDetailsRemoteRepository {
    /**
     * Requests recipe details from the network
     *
     * @param recipeId The ID of the recipe.
     * @return RecipeDetails
     *
     * @throws java.io.IOException in case of a network failure
     * @throws RuntimeException in other failures
     */
    suspend fun requestRecipeDetails(
        recipeId: Int,
    ): RecipeDetails
}
