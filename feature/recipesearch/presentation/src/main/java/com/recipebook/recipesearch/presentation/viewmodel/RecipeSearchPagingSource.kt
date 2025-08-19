package com.recipebook.recipesearch.presentation.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.recipebook.logging.LogLevel
import com.recipebook.logging.debugLog
import com.recipebook.recipesearch.domain.api.RecipeSearchInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal const val RECIPE_SEARCH_PAGE_SIZE = 10
private const val LOG_TAG = "RecipeSearchPagingSource"

internal class RecipeSearchPagingSource @AssistedInject constructor(
    @Assisted private val query: String,
    private val recipeSearchInteractor: RecipeSearchInteractor,
) : PagingSource<Int, RecipeSearchListItemState>() {

    override fun getRefreshKey(state: PagingState<Int, RecipeSearchListItemState>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(RECIPE_SEARCH_PAGE_SIZE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(RECIPE_SEARCH_PAGE_SIZE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipeSearchListItemState> {
        return try {
            val page = params.key ?: 0
            val response = recipeSearchInteractor.requestRecipes(
                query = query,
                offset = page,
                number = RECIPE_SEARCH_PAGE_SIZE,
            )

            LoadResult.Page(
                data = response.recipes.map { recipe ->
                    RecipeSearchListItemState(
                        recipe.id,
                        imageUrl = recipe.imageUrl,
                        name = recipe.title,
                        description = recipe.summary,
                    )
                },
                prevKey = if (page == 0) null else page - RECIPE_SEARCH_PAGE_SIZE,
                nextKey = if (response.recipes.isEmpty()) null else page + RECIPE_SEARCH_PAGE_SIZE,
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
        fun create(query: String): RecipeSearchPagingSource
    }
}
