package com.recipebook.datasource.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SpoonacularRecipeSortOption {
    @SerialName("calories")
    CALORIES,
}
