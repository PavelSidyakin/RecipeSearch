package com.example.recipesearch.mainnavigation.mainactivity

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
import com.example.recipesearch.navigationdestination.NavigationDestination
import com.example.recipesearch.presentation.screen.RecipeSearchScreen
import com.example.recipesearch.uikit.theme.RecipeSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                        startDestination = NavigationDestination.RecipeSearch.route,
                    ) {
                        composable(
                            route = NavigationDestination.RecipeSearch.route,
                        ) {
                            RecipeSearchScreen(
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    }
                }
            }
        }
    }
}
