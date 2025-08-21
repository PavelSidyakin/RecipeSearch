package com.recipebook.recipesearch.presentation.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.recipebook.logging.LogLevel
import com.recipebook.logging.debugLog
import com.recipebook.recipesearch.domain.api.RecipeSearchInteractor
import com.recipebook.recipesearch.domain.model.SearchResultSortOption
import com.recipebook.recipesearch.presentation.model.RecipeSearchListItemState
import com.recipebook.recipesearch.presentation.model.RecipeSearchSortOption
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal const val RECIPE_SEARCH_PAGE_SIZE = 10
private const val LOG_TAG = "RecipeSearchPagingSource"

internal class RecipeSearchPagingSource @AssistedInject constructor(
    @Assisted private val query: String,
    @Assisted private val sortOption: RecipeSearchSortOption,
    private val recipeSearchInteractor: RecipeSearchInteractor,
) : PagingSource<Int, RecipeSearchListItemState>() {

    override fun getRefreshKey(state: PagingState<Int, RecipeSearchListItemState>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipeSearchListItemState> {
        val offset = params.key ?: 0

        return try {
            val response = recipeSearchInteractor.requestRecipes(
                query = query,
                offset = offset,
                number = RECIPE_SEARCH_PAGE_SIZE,
                sortOption = when (sortOption) {
                    RecipeSearchSortOption.PRICE_ASCENDING -> SearchResultSortOption.PRICE_ASCENDING
                    RecipeSearchSortOption.PRICE_DESCENDING -> SearchResultSortOption.PRICE_DESCENDING
                },
            )
            val nextKey = when {
                response.number + response.offset >= response.totalResults -> null
                else -> offset + response.recipes.size
            }

            LoadResult.Page(
                data = response.recipes.map { recipe ->
                    RecipeSearchListItemState(
                        recipe.id,
                        imageUrl = recipe.imageUrl,
                        name = recipe.title,
                        description = recipe.summary,
                        price = recipe.price,
                    )
                },
                prevKey = null,
                nextKey = nextKey,
            )
        } catch (error: Throwable) {
            debugLog {
                tag = LOG_TAG
                level = LogLevel.ERROR
                message = "Failed to request data (Throwable). ${error.message}"
                throwable = error
            }
            LoadResult.Error(error)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(query: String, sortOption: RecipeSearchSortOption): RecipeSearchPagingSource
    }
}
