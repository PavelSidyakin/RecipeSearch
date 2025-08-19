package com.recipebook.recipesearch.presentation.viewmodel

import androidx.paging.compose.LazyPagingItems

internal data class RecipeSearchScreenState(
    val searchText: String,
    val lazyPagingItems: LazyPagingItems<RecipeSearchListItemState>?,
) {
    companion object {
        val initialState = RecipeSearchScreenState(
            searchText = "",
            lazyPagingItems = null,
        )
    }
}
