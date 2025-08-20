package com.recipebook.recipesearch.domain.impl

import com.recipebook.recipesearch.data.api.RecipeSearchRepository
import com.recipebook.recipesearch.domain.model.RecipesWithPagingInfo
import com.recipebook.recipesearch.domain.model.SearchResultSortOption
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class RecipeSearchInteractorImplTest {
    private val recipeSearchRepository: RecipeSearchRepository = mockk()

    private val subject = RecipeSearchInteractorImpl(
        recipeSearchRepository = recipeSearchRepository,
    )

    @Test
    @DisplayName(
        "Given repository.requestRecipes() returns data, " +
                "When requestRecipes() is called, " +
                "Then the data is returned as is"
    )
    fun requestFavoriteViewedRecipesTest0() {
        val query = "query"
        val offset = 111
        val number = 222
        val sortOption: SearchResultSortOption = mockk()

        val data: RecipesWithPagingInfo = mockk()

        // given
        coEvery {
            recipeSearchRepository.requestRecipes(query, offset, number, sortOption)
        } returns data

        runTest {
            // when
            val result = subject.requestRecipes(query, offset, number, sortOption)

            // then
            assertEquals(data, result)
        }
    }
}
