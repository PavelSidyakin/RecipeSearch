package com.recipebook.datasource.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SpoonacularRecipeSortDirection {
    @SerialName("asc")
    ASCENDING,

    @SerialName("desc")
    DESCENDING,
}
