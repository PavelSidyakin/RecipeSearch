package com.example.recipesearch.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipesearch.presentation.viewmodel.RecipeSearchViewModel

@Composable
fun RecipeSearchScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: RecipeSearchViewModel = hiltViewModel()

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        viewModel.onLaunch()
        onDispose { viewModel.onDispose() }
    }

    Text(
        modifier = modifier,
        text = state.text,
    )
}
