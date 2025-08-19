package com.recipebook.datasource.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class SpoonacularRecipeInformation(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("image")
    val imageUrl: String,

    @SerialName("extendedIngredients")
    val ingredients: List<SpoonacularRecipeIngredient>,

    @SerialName("summary")
    val summary: String,

    @SerialName("sourceUrl")
    val sourceWebsiteLink: String,
)
