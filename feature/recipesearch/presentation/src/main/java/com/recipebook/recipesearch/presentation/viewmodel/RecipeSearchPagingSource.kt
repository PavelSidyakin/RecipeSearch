package com.recipebook.recipesearch.presentation.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.recipebook.logging.LogLevel
import com.recipebook.logging.debugLog
import com.recipebook.recipesearch.domain.api.RecipeSearchInteractor
import com.recipebook.recipesearch.domain.model.SearchResultSortOption
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal const val RECIPE_SEARCH_PAGE_SIZE = 50
private const val LOG_TAG = "RecipeSearchPagingSource"

internal class RecipeSearchPagingSource @AssistedInject constructor(
    @Assisted private val query: String,
    @Assisted private val sortOption: SearchResultSortOption,
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
                sortOption = sortOption,
            )
            val nextKey = when {
                response.recipes.isEmpty() -> null
                else -> offset + response.recipes.size
            }

            LoadResult.Page(
                data = response.recipes.map { recipe ->
                    RecipeSearchListItemState(
                        recipe.id,
                        imageUrl = recipe.imageUrl,
                        name = recipe.title,
                        description = recipe.summary,
                    )
                },
                prevKey = null,
                nextKey = nextKey,
            )
        } catch (error: Exception) {
            debugLog {
                tag = LOG_TAG
                level = LogLevel.ERROR
                message = "Failed to request data "
                throwable = error
            }
            LoadResult.Error(error)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(query: String, sortOption: SearchResultSortOption): RecipeSearchPagingSource
    }
}
