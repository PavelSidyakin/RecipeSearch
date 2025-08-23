package com.recipebook.datasource.local.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.recipebook.datasource.local.db.RecipeBookTestDatabase
import com.recipebook.datasource.local.db.entity.ViewedRecipeEntity
import com.recipebook.testutils.collectEmits
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertNull
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ViewedRecipeDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dao: ViewedRecipeDao by lazy {
        RecipeBookTestDatabase.createDatabase().viewedRecipeDaoDao()
    }

    @Test
    fun `Given updateOrInsertViewedRecipe() is called, When requestViewedRecipe() is called, Then the result has the same data`() =
        runTest {
            val recipeId = 1111
            val recipeName = "recipeName"
            val recipeImageUrl = "recipeImageUrl"
            val ingredients = "ingredient0\ningredient1\ningredient2"
            val instructions = "instructions"
            val sourceWebsiteLink = "sourceWebsiteLink"
            val isFavorite = true
            val price = 1111.111f

            val recipeEntity = ViewedRecipeEntity(
                recipeId = recipeId,
                recipeName = recipeName,
                recipeImageUrl = recipeImageUrl,
                ingredients = ingredients,
                instructions = instructions,
                sourceWebsiteLink = sourceWebsiteLink,
                isFavorite = isFavorite,
                price = price,
            )

            // given
            dao.updateOrInsertViewedRecipe(recipeEntity)

            // when
            val result = dao.requestViewedRecipe(recipeId)

            // then
            assertEquals(recipeEntity, result)
        }

    @Test
    fun `Given updateOrInsertViewedRecipe() is called, When requestViewedRecipes() is called, Then the result has the same data`() =
        runTest {
            val recipeId0 = 1111
            val recipeName0 = "recipeName0"
            val recipeImageUrl0 = "recipeImageUrl0"
            val ingredients0 = "0ingredient0\ningredient1\ningredient2"
            val instructions0 = "instructions0"
            val sourceWebsiteLink0 = "sourceWebsiteLink0"
            val isFavorite0 = true
            val price0 = 1111.111f

            val recipeId1 = 2222
            val recipeName1 = "recipeName1"
            val recipeImageUrl1 = "recipeImageUrl1"
            val ingredients1 = "1ingredient1\ningredient1\ningredient2"
            val instructions1 = "instructions1"
            val sourceWebsiteLink1 = "sourceWebsiteLink1"
            val isFavorite1 = false
            val price1 = 2222.2222f

            val recipeEntity0 = ViewedRecipeEntity(
                recipeId = recipeId0,
                recipeName = recipeName0,
                recipeImageUrl = recipeImageUrl0,
                ingredients = ingredients0,
                instructions = instructions0,
                sourceWebsiteLink = sourceWebsiteLink0,
                isFavorite = isFavorite0,
                price = price0,
            )

            val recipeEntity1 = ViewedRecipeEntity(
                recipeId = recipeId1,
                recipeName = recipeName1,
                recipeImageUrl = recipeImageUrl1,
                ingredients = ingredients1,
                instructions = instructions1,
                sourceWebsiteLink = sourceWebsiteLink1,
                isFavorite = isFavorite1,
                price = price1,
            )

            // given
            dao.updateOrInsertViewedRecipe(recipeEntity0)
            dao.updateOrInsertViewedRecipe(recipeEntity1)

            // when
            val result = dao.requestViewedRecipes()

            // then
            assertAll(
                { assertEquals(recipeEntity0, result[0]) },
                { assertEquals(recipeEntity1, result[1]) },
            )
        }

    @Test
    fun `Given updateOrInsertViewedRecipe() is not called, When requestViewedRecipe() is called, Then the result is null`() =
        runTest {
            val result = dao.requestViewedRecipe(1111)

            assertNull(result)
        }

    @Test
    fun `Given updateOrInsertViewedRecipe() is not called, When requestViewedRecipes() is called, Then the result is empty`() =
        runTest {
            val result = dao.requestViewedRecipes()

            assertTrue(result.isEmpty())
        }

    @Test
    fun `Given updateOrInsertViewedRecipe() is not called, When requestFavoriteViewedRecipes() is called, Then the result is empty`() =
        runTest {
            val result = dao.requestFavoriteViewedRecipes()

            assertTrue(result.isEmpty())
        }

    @Test
    fun `Given a recipe is in the DB, When updateIsFavorite() is called, Then the recipe has the new isFavorite value`() =
        runTest {
            val recipeId = 1111

            val recipeEntity = ViewedRecipeEntity(
                recipeId = recipeId,
                recipeName = "",
                recipeImageUrl = "",
                ingredients = "",
                instructions = "",
                sourceWebsiteLink = "",
                isFavorite = false,
                price = 0f,
            )

            // given
            dao.updateOrInsertViewedRecipe(recipeEntity)

            // when
            dao.updateIsFavorite(recipeId, true)

            val result = dao.requestViewedRecipe(recipeId)

            // then
            assertTrue(result?.isFavorite == true)
        }

    @Test
    fun `Given a recipe is in the DB and observeViewedRecipe() is subscribed, When the recipe is updated, Then observeViewedRecipe() emits the new data`() =
        runTest {
            val recipeId = 1111

            val recipeName = "recipeName"
            val recipeImageUrl = "recipeImageUrl"
            val ingredients = "ingredient0\ningredient1\ningredient2"
            val instructions = "instructions"
            val sourceWebsiteLink = "sourceWebsiteLink"
            val isFavorite = true
            val price = 1111.111f

            val newRecipeName = "newRecipeName"
            val newRecipeImageUrl = "newRecipeImageUrl"
            val newIngredients = "newIngredients0\nnewIngredients\nnewIngredients"
            val newInstructions = "newInstructions"
            val newSourceWebsiteLink = "newSourceWebsiteLink"
            val newIsFavorite = false
            val newPrice = 2222.2222f

            val currentRecipeEntity = ViewedRecipeEntity(
                recipeId = recipeId,
                recipeName = recipeName,
                recipeImageUrl = recipeImageUrl,
                ingredients = ingredients,
                instructions = instructions,
                sourceWebsiteLink = sourceWebsiteLink,
                isFavorite = isFavorite,
                price = price,
            )

            val newRecipeEntity = ViewedRecipeEntity(
                recipeId = recipeId,
                recipeName = newRecipeName,
                recipeImageUrl = newRecipeImageUrl,
                ingredients = newIngredients,
                instructions = newInstructions,
                sourceWebsiteLink = newSourceWebsiteLink,
                isFavorite = newIsFavorite,
                price = newPrice,
            )

            // given
            dao.updateOrInsertViewedRecipe(currentRecipeEntity)
            val emits = dao.observeViewedRecipe(recipeId).collectEmits {
                // when
                dao.updateOrInsertViewedRecipe(newRecipeEntity)
            }

            // then
            assertEquals(newRecipeEntity, emits[1])
        }

    @Test
    fun `Given a recipe is not in the DB, When observeViewedRecipe() is subscribed, Then observeViewedRecipe() emits null`() =
        runTest {
            // when
            val emits = dao.observeViewedRecipe(11111).collectEmits { }

            // then
            assertNull(emits[0])
        }
}
