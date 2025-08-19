package com.recipebook.recipesearch.presentation.viewmodel

import androidx.paging.compose.LazyPagingItems
import com.recipebook.recipesearch.domain.model.SearchResultSortOption

internal data class RecipeSearchScreenState(
    val searchText: String,
    val lazyPagingItems: LazyPagingItems<RecipeSearchListItemState>?,
    val sortOption: SearchResultSortOption,
) {
    companion object {
        val initialState = RecipeSearchScreenState(
            searchText = "",
            lazyPagingItems = null,
            sortOption = SearchResultSortOption.CALORIES_ASCENDING,
        )
    }
}
