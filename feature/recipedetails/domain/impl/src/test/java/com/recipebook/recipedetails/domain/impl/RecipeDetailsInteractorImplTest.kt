package com.recipebook.recipedetails.domain.impl

import com.recipebook.recipedetails.data.api.RecipeDetailsLocalRepository
import com.recipebook.recipedetails.data.api.RecipeDetailsRemoteRepository
import com.recipebook.recipedetails.domain.model.RecipeDetails
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class RecipeDetailsInteractorImplTest {
    private val recipeDetailsLocalRepository: RecipeDetailsLocalRepository = mockk()
    private val recipeDetailsRemoteRepository: RecipeDetailsRemoteRepository = mockk()

    val subject = RecipeDetailsInteractorImpl(
        recipeDetailsLocalRepository = recipeDetailsLocalRepository,
        recipeDetailsRemoteRepository = recipeDetailsRemoteRepository,
    )

    @Test
    @DisplayName(
        "Given recipeDetailsLocalRepository.observeRecipeDetails() returns a Flow, " +
                "When observeRecipeDetails() is called, " +
                "Then the Flow is as returned from the repository"
    )
    fun observeRecipeDetailsTest0() {
        val recipeId = 1111
        val recipeDetailsFlow: Flow<RecipeDetails> = mockk()

        // given
        every { recipeDetailsLocalRepository.observeRecipeDetails(recipeId) } returns recipeDetailsFlow

        // when
        val result = subject.observeRecipeDetails(recipeId)

        // then
        assertEquals(recipeDetailsFlow, result)
    }

    @ParameterizedTest(name = "isFavorite={0}")
    @ValueSource(booleans = [true, false])
    @DisplayName(
        "When setFavorite() is called, " +
                "Then recipeDetailsLocalRepository.setFavorite() is called"
    )
    fun setFavoriteTest0(isFavorite: Boolean) {
        val recipeId = 1111
        // mock
        coEvery { recipeDetailsLocalRepository.setFavorite(recipeId, isFavorite) } just Runs

        runTest {
            // when
            subject.setFavorite(recipeId, isFavorite)

            // then
            coVerify { recipeDetailsLocalRepository.setFavorite(recipeId, isFavorite) }
        }
    }

    @Test
    @DisplayName(
        "Given the recipe is not in the local repository, " +
                "And the remote repository returns a result, " +
                "When requestRecipeDetails() is called, " +
                "Then the remote result is saved in the local repository, " +
                "And the remote result is returned"
    )
    fun requestRecipeDetailsTest0() {
        val recipeId = 1111
        val remoteRecipeDetails: RecipeDetails = mockk()

        // mock
        coEvery { recipeDetailsLocalRepository.saveRecipe(remoteRecipeDetails) } just Runs

        // given
        coEvery { recipeDetailsLocalRepository.requestRecipeDetails(recipeId) } returns null
        coEvery { recipeDetailsRemoteRepository.requestRecipeDetails(recipeId) } returns remoteRecipeDetails

        runTest {
            // when
            val result = subject.requestRecipeDetails(recipeId)

            // then
            assertAll(
                { assertEquals(remoteRecipeDetails, result) },
                { coVerify { recipeDetailsLocalRepository.saveRecipe(remoteRecipeDetails) } },
            )
        }
    }

    @Test
    @DisplayName(
        "Given the recipe is in the local repository, " +
                "When requestRecipeDetails() is called, " +
                "Then the local result is returned, " +
                "And the remote repository is not called"
    )
    fun requestRecipeDetailsTest1() {
        val recipeId = 1111
        val localRecipeDetails: RecipeDetails = mockk()

        // mock
        coEvery { recipeDetailsRemoteRepository.requestRecipeDetails(any()) } returns mockk()

        // given
        coEvery { recipeDetailsLocalRepository.requestRecipeDetails(recipeId) } returns localRecipeDetails

        runTest {
            // when
            val result = subject.requestRecipeDetails(recipeId)

            // then
            assertAll(
                { assertEquals(localRecipeDetails, result) },
                { coVerify(exactly = 0) { recipeDetailsRemoteRepository.requestRecipeDetails(any()) } },
            )
        }
    }
}
