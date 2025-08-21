package com.recipebook.viewedrecipes.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.recipebook.strings.R
import com.recipebook.uikit.size.Padding
import com.recipebook.uikit.theme.RecipeSearchTheme
import com.recipebook.uikit.widgets.ScreenHeader
import com.recipebook.viewedrecipes.presentation.model.ViewedRecipesItemState
import com.recipebook.viewedrecipes.presentation.model.ViewedRecipesScreenState
import com.recipebook.viewedrecipes.presentation.viewmodel.ViewedRecipesExternalEvent
import com.recipebook.viewedrecipes.presentation.viewmodel.ViewedRecipesViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * The viewed recipes screen.
 *
 * @param onRecipeClicked The callback is called when a recipe is clicked.
 * @param onBackButtonClicked The callback to be called when the back button is clicked.
 * @param modifier Optional [Modifier]
 */
@Composable
fun ViewedRecipesScreen(
    onRecipeClicked: (recipeId: Int) -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: ViewedRecipesViewModel = hiltViewModel()

    val state by viewModel.stateFlow.collectAsState()

    DisposableEffect(Unit) {
        viewModel.onLaunch()
        onDispose { viewModel.onDispose() }
    }

    LaunchedEffect(Unit) {
        viewModel.externalEventsFlow
            .onEach { event ->
                when (event) {
                    is ViewedRecipesExternalEvent.OnRecipeClicked -> onRecipeClicked(event.recipeId)
                    ViewedRecipesExternalEvent.OnBackButtonClicked -> onBackButtonClicked()
                }
            }
            .launchIn(this)
    }

    ViewedRecipesScreenImpl(
        modifier = modifier,
        state = state,
        onRecipeClicked = viewModel::onRecipeClick,
        onShowFavoritesOnlySwitchChange = viewModel::onShowFavoritesOnlySwitchChange,
        onBackButtonClicked = viewModel::onBackButtonClicked,
    )
}

@Composable
private fun ViewedRecipesScreenImpl(
    state: ViewedRecipesScreenState,
    onShowFavoritesOnlySwitchChange: (Boolean) -> Unit,
    onRecipeClicked: (recipeId: Int) -> Unit,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        ScreenHeader(
            title = stringResource(R.string.viewed_recipes_title),
            onBackButtonClicked = onBackButtonClicked
        )
        Row(
            modifier = Modifier
                .padding(horizontal = Padding.Quad)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.viewed_recipes_only_favorites_filter),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Switch(
                checked = state.isFavoriteFilter,
                onCheckedChange = onShowFavoritesOnlySwitchChange,
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(all = Padding.Double),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
        ) {
            items(
                items = state.items,
                key = { it.recipeId },
            ) { itemState ->
                ViewedRecipeItem(
                    modifier = Modifier
                        .padding(all = Padding.Double)
                        .clickable(
                            onClick = {
                                onRecipeClicked(itemState.recipeId)
                            },
                        ),
                    state = itemState,
                )
            }
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF000000, showBackground = true)
@Preview(device = Devices.TABLET)
private fun ViewedRecipesScreenImplPreview() {
    RecipeSearchTheme {
        ViewedRecipesScreenImpl(
            modifier = Modifier.fillMaxSize(),
            state = ViewedRecipesScreenState(
                isFavoriteFilter = true,
                items = List(10) { index ->
                    ViewedRecipesItemState(
                        recipeId = index,
                        imageUrl = "https://xxx",
                        name = "Name Name Name Name Name",
                        description = "Description Description Description Description Description Description",
                        price = 80.22f,
                        isFavorite = index % 2 == 0,
                    )
                }
            ),
            onShowFavoritesOnlySwitchChange = { },
            onRecipeClicked = { },
            onBackButtonClicked = { },
        )
    }
}
