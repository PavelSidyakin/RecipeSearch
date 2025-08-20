package com.recipebook.recipesearch.data.impl

import com.recipebook.datasource.remote.SpoonacularRemoteDataSource
import com.recipebook.datasource.remote.model.SpoonacularRecipeBriefInfo
import com.recipebook.datasource.remote.model.SpoonacularRecipeResponse
import com.recipebook.datasource.remote.model.SpoonacularRecipeSortDirection
import com.recipebook.datasource.remote.model.SpoonacularRecipeSortOption
import com.recipebook.recipesearch.domain.model.RecipeBriefInfo
import com.recipebook.recipesearch.domain.model.RecipesWithPagingInfo
import com.recipebook.recipesearch.domain.model.SearchResultSortOption
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.support.ParameterDeclarations
import java.util.stream.Stream

internal class RecipeSearchRepositoryImplTest {
    private val spoonacularRemoteDataSource: SpoonacularRemoteDataSource = mockk()

    private val subject = RecipeSearchRepositoryImpl(
        spoonacularRemoteDataSource = spoonacularRemoteDataSource,
    )

    @ParameterizedTest
    @ArgumentsSource(SortOptionsMapperArgumentProvider::class)
    @DisplayName(
        "Given the data source returns recipes, " +
                "When requestRecipes() is called, " +
                "Then the recipes are returned and filled correctly"
    )
    fun requestRecipesTest0(
        sortOption: SearchResultSortOption,
        spoonacularSortOption: SpoonacularRecipeSortOption,
        spoonacularSortDirection: SpoonacularRecipeSortDirection,
    ) {
        // request data
        val query = "query"
        val offsetRequest = 999
        val numberRequest = 888

        // result data
        val offset = 11
        val number = 22
        val totalResults = 33

        val recipeId0 = 1111
        val recipeTitle0 = "recipeTitle0"
        val recipeImageUrl0 = "recipeImageUrl0"
        val summary0 = "summary0"
        val price0 = 1111.111f

        val recipeId1 = 2222
        val recipeTitle1 = "recipeTitle1"
        val recipeImageUrl1 = "recipeImageUrl1"
        val summary1 = "summary1"
        val price1 = 2222.2222f

        // given
        coEvery {
            spoonacularRemoteDataSource.requestRecipes(
                query = query,
                offset = offsetRequest,
                number = numberRequest,
                sortOption = spoonacularSortOption,
                sortDirection = spoonacularSortDirection,
            )
        } returns SpoonacularRecipeResponse(
            offset = offset,
            number = number,
            totalResults = totalResults,
            recipes = listOf(
                SpoonacularRecipeBriefInfo(
                    id = recipeId0,
                    imageUrl = recipeImageUrl0,
                    title = recipeTitle0,
                    summary = summary0,
                    pricePerServing = price0,
                ),
                SpoonacularRecipeBriefInfo(
                    id = recipeId1,
                    imageUrl = recipeImageUrl1,
                    title = recipeTitle1,
                    summary = summary1,
                    pricePerServing = price1,
                ),
            ),
        )

        runTest {
            // when
            val result = subject.requestRecipes(
                query = query,
                offset = offsetRequest,
                number = numberRequest,
                sortOption = sortOption,
            )

            val expectedRecipesWithPagingInfo = RecipesWithPagingInfo(
                offset = offset,
                number = number,
                totalResults = totalResults,
                recipes = listOf(
                    RecipeBriefInfo(
                        id = recipeId0,
                        imageUrl = recipeImageUrl0,
                        title = recipeTitle0,
                        summary = summary0,
                        price = price0,
                    ),
                    RecipeBriefInfo(
                        id = recipeId1,
                        imageUrl = recipeImageUrl1,
                        title = recipeTitle1,
                        summary = summary1,
                        price = price1,
                    )
                ),
            )

            // then
            assertEquals(expectedRecipesWithPagingInfo, result)
        }
    }

    private class SortOptionsMapperArgumentProvider : ArgumentsProvider {
        override fun provideArguments(
            parameters: ParameterDeclarations,
            context: ExtensionContext,
        ): Stream<out Arguments> {
            return SearchResultSortOption.entries.map { searchResultSortOption ->
                when (searchResultSortOption) {
                    SearchResultSortOption.CALORIES_ASCENDING -> Triple(
                        searchResultSortOption,
                        SpoonacularRecipeSortOption.CALORIES,
                        SpoonacularRecipeSortDirection.ASCENDING,
                    )

                    SearchResultSortOption.CALORIES_DESCENDING -> Triple(
                        searchResultSortOption,
                        SpoonacularRecipeSortOption.CALORIES,
                        SpoonacularRecipeSortDirection.DESCENDING,
                    )

                    SearchResultSortOption.PRICE_ASCENDING -> Triple(
                        searchResultSortOption,
                        SpoonacularRecipeSortOption.PRICE,
                        SpoonacularRecipeSortDirection.ASCENDING,
                    )

                    SearchResultSortOption.PRICE_DESCENDING -> Triple(
                        searchResultSortOption,
                        SpoonacularRecipeSortOption.PRICE,
                        SpoonacularRecipeSortDirection.DESCENDING,
                    )
                }
            }.map { Arguments.of(it.first, it.second, it.third) }.stream()
        }
    }
}
