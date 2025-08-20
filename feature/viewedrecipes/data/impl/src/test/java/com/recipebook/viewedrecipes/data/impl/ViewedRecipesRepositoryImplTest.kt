package com.recipebook.viewedrecipes.data.impl

import com.recipebook.datasource.local.db.dao.ViewedRecipeDao
import com.recipebook.datasource.local.db.entity.ViewedRecipeEntity
import com.recipebook.testutils.DispatcherProviderStub
import com.recipebook.viewedrecipes.domain.model.ViewedRecipeDetails
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class ViewedRecipesRepositoryImplTest {
    private val viewedRecipeDao: ViewedRecipeDao = mockk()

    private val subject = ViewedRecipesRepositoryImpl(
        viewedRecipeDao = viewedRecipeDao,
        dispatcherProvider = DispatcherProviderStub(),
    )

    @ParameterizedTest(name = "isFavoriteRequest={0}")
    @ValueSource(booleans = [true, false])
    @DisplayName(
        "Given the DTO returns recipes, " +
                "When requestViewedRecipes() is called, " +
                "Then the recipes are returned and filled correctly"
    )
    fun requestViewedRecipesTest0(isFavoriteRequest: Boolean) {
        val recipeId0 = 1111
        val recipeName0 = "recipeName0"
        val recipeImageUrl0 = "recipeImageUrl0"
        val description0 = "description0"
        val isFavorite0 = true
        val price0 = 1111.111f

        val recipeId1 = 2222
        val recipeName1 = "recipeName1"
        val recipeImageUrl1 = "recipeImageUrl1"
        val description1 = "description1"
        val isFavorite1 = false
        val price1 = 2222.2222f

        // given
        every {
            when (isFavoriteRequest) {
                true -> viewedRecipeDao.requestFavoriteViewedRecipes()
                false -> viewedRecipeDao.requestViewedRecipes()
            }
        } returns listOf(
            ViewedRecipeEntity(
                recipeId = recipeId0,
                recipeName = recipeName0,
                recipeImageUrl = recipeImageUrl0,
                instructions = description0,
                isFavorite = isFavorite0,
                price = price0,
                ingredients = "",
                sourceWebsiteLink = "",
            ),
            ViewedRecipeEntity(
                recipeId = recipeId1,
                recipeName = recipeName1,
                recipeImageUrl = recipeImageUrl1,
                instructions = description1,
                isFavorite = isFavorite1,
                price = price1,
                ingredients = "",
                sourceWebsiteLink = "",
            ),
        )

        runTest {
            // when
            val result = when (isFavoriteRequest) {
                true -> subject.requestFavoriteViewedRecipes()
                false -> subject.requestViewedRecipes()
            }

            val expectedDetails0 = ViewedRecipeDetails(
                recipeId = recipeId0,
                recipeName = recipeName0,
                recipeImageUrl = recipeImageUrl0,
                description = description0,
                isFavorite = isFavorite0,
                price = price0,
            )

            val expectedDetails1 = ViewedRecipeDetails(
                recipeId = recipeId1,
                recipeName = recipeName1,
                recipeImageUrl = recipeImageUrl1,
                description = description1,
                isFavorite = isFavorite1,
                price = price1,
            )

            // then
            assertAll(
                { assertEquals(expectedDetails0, result[0]) },
                { assertEquals(expectedDetails1, result[1]) },
            )
        }
    }
}
