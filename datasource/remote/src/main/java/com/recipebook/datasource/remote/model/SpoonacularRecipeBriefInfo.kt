package com.recipebook.datasource.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class SpoonacularRecipeBriefInfo(
    @SerialName("id")
    val id: Int,

    @SerialName("image")
    val imageUrl: String,

    @SerialName("title")
    val title: String,

    @SerialName("summary")
    val summary: String,

    @SerialName("pricePerServing")
    val pricePerServing: Float,
)
