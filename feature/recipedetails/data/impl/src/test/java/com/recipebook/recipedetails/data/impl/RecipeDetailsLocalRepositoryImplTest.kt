package com.recipebook.recipedetails.data.impl

import com.recipebook.datasource.local.db.dao.ViewedRecipeDao
import com.recipebook.datasource.local.db.entity.ViewedRecipeEntity
import com.recipebook.recipedetails.domain.model.RecipeDetails
import com.recipebook.testutils.DispatcherProviderStub
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class RecipeDetailsLocalRepositoryImplTest {
    private val viewedRecipeDao: ViewedRecipeDao = mockk()

    private val subject = RecipeDetailsLocalRepositoryImpl(
        viewedRecipeDao = viewedRecipeDao,
        dispatcherProvider = DispatcherProviderStub(),
    )

    @Test
    @DisplayName(
        "Given the DAO returns a Flow with recipe details, " +
                "When observeRecipeDetails() is subscribed, " +
                "Then the emitted data is filled correctly"
    )
    fun observeRecipeDetailsTest0() {
        val recipeId = 1111
        val recipeName = "recipeName"
        val recipeImageUrl = "recipeImageUrl"
        val ingredients = "ingredient0\ningredient1\ningredient2"
        val instructions = "instructions"
        val sourceWebsiteLink = "sourceWebsiteLink"
        val isFavorite = true
        val price = 1111.111f

        // given
        every { viewedRecipeDao.observeViewedRecipe(recipeId) } returns flowOf(
            ViewedRecipeEntity(
                recipeId = recipeId,
                recipeName = recipeName,
                recipeImageUrl = recipeImageUrl,
                ingredients = ingredients,
                instructions = instructions,
                sourceWebsiteLink = sourceWebsiteLink,
                isFavorite = isFavorite,
                price = price,
            ),
        )

        runTest {
            // when
            val result = subject.observeRecipeDetails(recipeId).first()

            val expectedRecipeDetails = RecipeDetails(
                recipeId = recipeId,
                recipeName = recipeName,
                recipeImageUrl = recipeImageUrl,
                ingredients = ingredients,
                instructions = instructions,
                sourceWebsiteLink = sourceWebsiteLink,
                isFavorite = isFavorite,
                price = price,
            )

            // then
            assertEquals(expectedRecipeDetails, result)
        }
    }

    @Test
    @DisplayName(
        "Given the DAO returns a recipe details, " +
                "When requestRecipeDetails() is subscribed, " +
                "Then the returned data is filled correctly"
    )
    fun requestRecipeDetailsTest0() {
        val recipeId = 1111
        val recipeName = "recipeName"
        val recipeImageUrl = "recipeImageUrl"
        val ingredients = "ingredient0\ningredient1\ningredient2"
        val instructions = "instructions"
        val sourceWebsiteLink = "sourceWebsiteLink"
        val isFavorite = true
        val price = 1111.111f

        // given
        every { viewedRecipeDao.requestViewedRecipe(recipeId) } returns ViewedRecipeEntity(
            recipeId = recipeId,
            recipeName = recipeName,
            recipeImageUrl = recipeImageUrl,
            ingredients = ingredients,
            instructions = instructions,
            sourceWebsiteLink = sourceWebsiteLink,
            isFavorite = isFavorite,
            price = price,
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
                isFavorite = isFavorite,
                price = price,
            )

            // then
            assertEquals(expectedRecipeDetails, result)
        }
    }

    @Test
    @DisplayName(
        "When saveRecipe() is called, " +
                "Then the data source is updated with the correct data"
    )
    fun saveRecipeTest0() {
        val recipeId = 1111
        val recipeName = "recipeName"
        val recipeImageUrl = "recipeImageUrl"
        val ingredients = "ingredient0\ningredient1\ningredient2"
        val instructions = "instructions"
        val sourceWebsiteLink = "sourceWebsiteLink"
        val isFavorite = true
        val price = 1111.111f

        val viewedRecipeEntitySlot = slot<ViewedRecipeEntity>()

        // mock
        coEvery {
            viewedRecipeDao.updateOrInsertViewedRecipe(capture(viewedRecipeEntitySlot))
        } just Runs

        runTest {
            // when
            subject.saveRecipe(
                RecipeDetails(
                    recipeId = recipeId,
                    recipeName = recipeName,
                    recipeImageUrl = recipeImageUrl,
                    ingredients = ingredients,
                    instructions = instructions,
                    sourceWebsiteLink = sourceWebsiteLink,
                    isFavorite = isFavorite,
                    price = price,
                )
            )

            val expectedViewedRecipeEntity = ViewedRecipeEntity(
                recipeId = recipeId,
                recipeName = recipeName,
                recipeImageUrl = recipeImageUrl,
                ingredients = ingredients,
                instructions = instructions,
                sourceWebsiteLink = sourceWebsiteLink,
                isFavorite = isFavorite,
                price = price,
            )

            // then
            assertEquals(expectedViewedRecipeEntity, viewedRecipeEntitySlot.captured)
        }
    }

    @ParameterizedTest(name = "isFavorite={0}")
    @ValueSource(booleans = [true, false])
    @DisplayName(
        "When setFavorite() is called, " +
                "Then the data source is updated with the correct data"
    )
    fun setFavoriteTest0(isFavorite: Boolean) {
        val recipeId = 1111
        // mock
        coEvery { viewedRecipeDao.updateIsFavorite(recipeId, isFavorite) } just Runs

        runTest {
            // when
            subject.setFavorite(recipeId, isFavorite)

            // then
            coVerify { viewedRecipeDao.updateIsFavorite(recipeId, isFavorite) }
        }
    }
}
