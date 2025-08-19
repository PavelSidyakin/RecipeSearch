package com.recipebook.datasource.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class SpoonacularRecipeResponse(
    @SerialName("results")
    val recipes: List<SpoonacularRecipeBriefInfo>,

    @SerialName("offset")
    val offset: Int,

    @SerialName("number")
    val number: Int,

    @SerialName("totalResults")
    val totalResults: Int,
)
