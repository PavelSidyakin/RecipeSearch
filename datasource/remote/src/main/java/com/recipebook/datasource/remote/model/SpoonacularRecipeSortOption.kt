package com.recipebook.datasource.remote.model

enum class SpoonacularRecipeSortOption(val value: String) {
    CALORIES("calories"),
    PRICE("price"),
    ;

    override fun toString(): String {
        return value
    }
}
