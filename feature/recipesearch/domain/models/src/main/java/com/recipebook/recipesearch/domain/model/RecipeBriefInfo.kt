package com.recipebook.recipesearch.domain.model

data class RecipeBriefInfo(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val summary: String,
)