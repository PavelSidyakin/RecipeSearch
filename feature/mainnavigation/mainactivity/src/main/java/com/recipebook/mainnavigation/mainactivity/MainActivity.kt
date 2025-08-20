package com.recipebook.mainnavigation.mainactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.recipebook.mainnavigation.NavigationDestination
import com.recipebook.recipedetails.presentation.screen.RecipeDetailsScreen
import com.recipebook.recipesearch.presentation.screen.RecipeSearchScreen
import com.recipebook.uikit.theme.RecipeSearchTheme
import com.recipebook.viewedrecipes.presentation.screen.ViewedRecipesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeSearchTheme {
                Scaffold { paddingValues ->
                    val navController = rememberNavController()
                    NavHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        startDestination = NavigationDestination.RecipeSearch,
                    ) {
                        composable<NavigationDestination.RecipeSearch> {
                            RecipeSearchScreen(
                                modifier = Modifier.fillMaxSize(),
                                onRecipeClicked = { recipeId ->
                                    navController.navigate(route = NavigationDestination.RecipeDetails(recipeId))
                                },
                                onViewedRecipesClicked = {
                                    navController.navigate(route = NavigationDestination.ViewedRecipes)
                                },
                            )
                        }
                        composable<NavigationDestination.RecipeDetails> { backStackEntry ->
                            val destination = backStackEntry.toRoute<NavigationDestination.RecipeDetails>()
                            RecipeDetailsScreen(
                                modifier = Modifier.fillMaxSize(),
                                recipeId = destination.recipeId,
                                onBackButtonClicked = { navController.popBackStack() },
                            )
                        }
                        composable<NavigationDestination.ViewedRecipes> {
                            ViewedRecipesScreen(
                                onRecipeClicked = { recipeId ->
                                    navController.navigate(route = NavigationDestination.RecipeDetails(recipeId))
                                },
                                onBackButtonClicked = { navController.popBackStack() },
                            )
                        }
                    }
                }
            }
        }
    }
}
