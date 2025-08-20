package com.recipebook.datasource.remote.model

enum class SpoonacularRecipeSortDirection(val value: String) {
    ASCENDING("asc"),
    DESCENDING("desc"),
    ;

    override fun toString(): String {
        return value
    }
}
