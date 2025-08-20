package com.recipebook.recipedetails.data.impl

import com.recipebook.datasource.remote.SpoonacularRemoteDataSource
import com.recipebook.datasource.remote.model.SpoonacularRecipeInformationResponse
import com.recipebook.datasource.remote.model.SpoonacularRecipeIngredient
import com.recipebook.recipedetails.domain.model.RecipeDetails
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class RecipeDetailsRemoteRepositoryImplTest {
    private val spoonacularRemoteDataSource: SpoonacularRemoteDataSource = mockk()

    private val subject = RecipeDetailsRemoteRepositoryImpl(
        spoonacularRemoteDataSource = spoonacularRemoteDataSource,
    )

    @Test
    @DisplayName(
        "Given the data source returns a recipe details, " +
                "When requestRecipeDetails() is called, " +
                "Then the recipe details are returned and filled correctly"
    )
    fun requestRecipeDetailsTest0() {
        val recipeId = 1111
        val recipeName = "recipeName"
        val recipeImageUrl = "recipeImageUrl"
        val ingredientsList = listOf("ingredient0", "ingredient1", "ingredient2")
        val ingredients = "ingredient0\ningredient1\ningredient2"
        val instructions = "instructions"
        val sourceWebsiteLink = "sourceWebsiteLink"
        val price = 1111.111f

        // given
        coEvery {
            spoonacularRemoteDataSource.requestRecipeInformation(recipeId)
        } returns SpoonacularRecipeInformationResponse(
            id = recipeId,
            title = recipeName,
            imageUrl = recipeImageUrl,
            ingredients = ingredientsList.map { SpoonacularRecipeIngredient(it) },
            summary = instructions,
            sourceWebsiteLink = sourceWebsiteLink,
            pricePerServing = price,
        )

        runTest {
            // when
            val result = subject.requestRecipeDetails(recipeId)

            val expectedRecipeDetails = RecipeDetails(
                recipeId = recipeId,
                recipeName = recipeName,
                recipeImageUrl = recipeImageUrl,
                ingredients = ingredients,
                instructions = instructions,
                sourceWebsiteLink = sourceWebsiteLink,
                isFavorite = false,
                price = price,
            )

            // then
            assertEquals(expectedRecipeDetails, result)
        }
    }
}
