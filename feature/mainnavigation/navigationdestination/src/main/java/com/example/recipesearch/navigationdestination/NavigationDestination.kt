package com.example.recipesearch.navigationdestination

sealed class NavigationDestination(val route: String) {
    data object RecipeSearch: NavigationDestination("RecipeSearch")
    data class RecipeDetails(val recipeId: Int): NavigationDestination("RecipeDetails$recipeId")
}
