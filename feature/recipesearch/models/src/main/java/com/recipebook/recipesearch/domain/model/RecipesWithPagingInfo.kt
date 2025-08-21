package com.recipebook.recipesearch.domain.model

/**
 * Recipes with paging data.
 */
data class RecipesWithPagingInfo(
    val recipes: List<RecipeBriefInfo>,

    val offset: Int,
    val number: Int,
    val totalResults: Int,
)
