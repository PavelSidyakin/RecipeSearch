package com.recipebook.datasource.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class SpoonacularRecipeIngredient(
    @SerialName("original")
    val ingredientDescription: String,
)
