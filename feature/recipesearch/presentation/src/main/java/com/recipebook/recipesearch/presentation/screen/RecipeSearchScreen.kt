package com.recipebook.recipesearch.presentation.screen

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.recipebook.recipesearch.presentation.model.RecipeSearchListItemState
import com.recipebook.recipesearch.presentation.model.RecipeSearchScreenState
import com.recipebook.recipesearch.presentation.model.RecipeSearchSortOption
import com.recipebook.recipesearch.presentation.viewmodel.RecipeSearchExternalEvent
import com.recipebook.recipesearch.presentation.viewmodel.RecipeSearchViewModel
import com.recipebook.strings.R
import com.recipebook.uikit.icons.History
import com.recipebook.uikit.icons.RsIcon
import com.recipebook.uikit.size.Padding
import com.recipebook.uikit.theme.RecipeSearchTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.IOException

@Composable
fun RecipeSearchScreen(
    onRecipeClicked: (recipeId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: RecipeSearchViewModel = hiltViewModel()

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()
    val pagingListInvalidate by viewModel.pagingListFlow.collectAsStateWithLifecycle(PagingData.empty())

    val lazyPagingItems: LazyPagingItems<RecipeSearchListItemState> = key(pagingListInvalidate) {
        viewModel.pagingListFlow.collectAsLazyPagingItems()
    }

    // Workaround over Paging 3 library.
    // We can't get LazyPagingItems other than calling composable function collectAsLazyPagingItems()
    // To have consistency is the state's updates via VM, call VM here and let the VM update the state
    DisposableEffect(lazyPagingItems) {
        viewModel.onLazyPagingItemsReady(lazyPagingItems)
        onDispose { }
    }

    LaunchedEffect(Unit) {
        viewModel.externalEventsFlow
            .onEach { event ->
                when (event) {
                    is RecipeSearchExternalEvent.OnRecipeClicked -> onRecipeClicked(event.recipeId)
                }
            }
            .launchIn(this)
    }

    RecipeSearchScreenImpl(
        modifier = modifier,
        state = state,
        onSearchTextChanged = viewModel::onSearchTextChanged,
        onCaloriesSortClicked = viewModel::onCaloriesSortClicked,
        onRecipeClicked = { viewModel.onRecipeClicked(it) },
    )

}

@Composable
private fun RecipeSearchScreenImpl(
    state: RecipeSearchScreenState,
    onSearchTextChanged: (String) -> Unit,
    onCaloriesSortClicked: () -> Unit,
    onRecipeClicked: (recipeId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(Padding.Quad)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.recipe_search_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Icon(
                imageVector = RsIcon.History,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var searchText by remember { mutableStateOf(state.searchText) }
            DisposableEffect(searchText) {
                onSearchTextChanged(searchText)
                onDispose { }
            }

            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = Padding.Quad),
                value = searchText,
                onValueChange = { searchText = it },
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.recipe_search_hint),
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
            )
            Text(
                modifier = Modifier
                    .padding(start = Padding.Double)
                    .clickable(
                        onClick = onCaloriesSortClicked,
                    ),
                text = state.sortOption.toText(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(all = Padding.Double),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
        ) {
            items(
                count = state.lazyPagingItems?.itemCount ?: 0,
            ) { index ->
                state.lazyPagingItems?.get(index)?.let { itemState ->
                    RecipeSearchListItem(
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

            state.lazyPagingItems.getPagingError()?.let { error ->
                item {
                    ErrorItem(
                        modifier = Modifier.fillMaxWidth(),
                        throwable = error,
                    )
                }
            }
        }
    }
}

private fun LazyPagingItems<RecipeSearchListItemState>?.getPagingError(): Throwable? {
    return ((this?.loadState?.source?.refresh as? LoadState.Error)
        ?: (this?.loadState?.source?.append as? LoadState.Error))?.error
}

@Composable
private fun ErrorItem(
    modifier: Modifier = Modifier,
    throwable: Throwable,
) {
    Text(
        modifier = modifier,
        text = when (throwable) {
            is IOException -> stringResource(R.string.error_network_connection)
            else -> stringResource(R.string.error_general)
        },
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.error,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun RecipeSearchSortOption.toText(): String {
    return when (this) {
        RecipeSearchSortOption.PRICE_ASCENDING -> stringResource(R.string.recipe_search_price_asc)
        RecipeSearchSortOption.PRICE_DESCENDING -> stringResource(R.string.recipe_search_price_desc)
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF000000, showBackground = true)
@Preview(device = Devices.TABLET)
private fun RecipeSearchScreenImplPreview() {
    RecipeSearchTheme {
        RecipeSearchScreenImpl(
            modifier = Modifier.fillMaxSize(),
            state = RecipeSearchScreenState(
                searchText = "",
                lazyPagingItems = null,
                sortOption = RecipeSearchSortOption.PRICE_DESCENDING,
            ),
            onSearchTextChanged = { },
            onCaloriesSortClicked = { },
            onRecipeClicked = { },
        )
    }
}
