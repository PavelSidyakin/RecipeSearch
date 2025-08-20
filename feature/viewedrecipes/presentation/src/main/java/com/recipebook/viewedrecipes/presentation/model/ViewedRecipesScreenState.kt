package com.recipebook.viewedrecipes.presentation.model

internal data class ViewedRecipesScreenState(
    val isFavoriteFilter: Boolean,
    val items: List<ViewedRecipesItemState>,
) {
    companion object {
        val initialState = ViewedRecipesScreenState(
            isFavoriteFilter = false,
            items = emptyList(),
        )
    }
}
