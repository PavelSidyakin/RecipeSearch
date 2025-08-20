package com.recipebook.viewedrecipes.domain.impl

import com.recipebook.viewedrecipes.data.api.ViewedRecipesRepository
import com.recipebook.viewedrecipes.domain.model.ViewedRecipeDetails
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class ViewedRecipesInteractorImplTest {
    private val viewedRecipesRepository: ViewedRecipesRepository = mockk()

    private val subject = ViewedRecipesInteractorImpl(
        viewedRecipesRepository = viewedRecipesRepository,
    )

    @Test
    @DisplayName(
        "Given repository.requestViewedRecipes() returns data, " +
                "When requestViewedRecipes() is called, " +
                "Then the data is returned as is"
    )
    fun requestViewedRecipesTest0() {
        val data: List<ViewedRecipeDetails> = listOf(mockk(), mockk())

        // given
        coEvery { viewedRecipesRepository.requestViewedRecipes() } returns data

        // when
        runTest {
            val result = subject.requestViewedRecipes()

            assertEquals(data, result)
        }
    }

    @Test
    @DisplayName(
        "Given repository.requestFavoriteViewedRecipes() returns data, " +
                "When requestFavoriteViewedRecipes() is called, " +
                "Then the data is returned as is"
    )
    fun requestFavoriteViewedRecipesTest0() {
        val data: List<ViewedRecipeDetails> = listOf(mockk(), mockk())

        // given
        coEvery { viewedRecipesRepository.requestFavoriteViewedRecipes() } returns data

        runTest {
            // when
            val result = subject.requestFavoriteViewedRecipes()

            // then
            assertEquals(data, result)
        }
    }
}
