package com.recipebook.recipesearch.domain.model

/**
 * Recipe brief info.
 */
data class RecipeBriefInfo(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val summary: String,
    val price: Float,
)