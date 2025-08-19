package com.recipebook.recipesearch.presentation.screen

import android.content.res.Configuration
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.recipebook.recipesearch.presentation.viewmodel.RecipeSearchListItemState
import com.recipebook.recipesearch.presentation.viewmodel.RecipeSearchScreenState
import com.recipebook.recipesearch.presentation.viewmodel.RecipeSearchViewModel
import com.recipebook.strings.R
import com.recipebook.uikit.icons.History
import com.recipebook.uikit.icons.RsIcon
import com.recipebook.uikit.size.Padding
import com.recipebook.uikit.theme.RecipeSearchTheme

@Composable
fun RecipeSearchScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel: RecipeSearchViewModel = hiltViewModel()

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    val lazyPagingItems: LazyPagingItems<RecipeSearchListItemState> = viewModel.pagingListFlow
        .collectAsLazyPagingItems()

    // Workaround over Paging 3 library.
    // We can't get LazyPagingItems other than calling composable function collectAsLazyPagingItems()
    // To have consistency is the state's updates via VM, call VM here and let VM update the state
    DisposableEffect(lazyPagingItems) {
        viewModel.onLazyPagingItemsReady(lazyPagingItems)
        onDispose { }
    }

    DisposableEffect(Unit) {
        viewModel.onLaunch()
        onDispose { viewModel.onDispose() }
    }

    RecipeSearchScreenImpl(
        modifier = modifier,
        state = state,
        onSearchTextChanged = viewModel::onSearchTextChanged
    )

}

@Composable
private fun RecipeSearchScreenImpl(
    state: RecipeSearchScreenState,
    onSearchTextChanged: (String) -> Unit,
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
        var searchText by remember { mutableStateOf(state.searchText) }
        DisposableEffect(searchText) {
            onSearchTextChanged(searchText)
            onDispose { }
        }

        OutlinedTextField(
            modifier = Modifier.padding(top = Padding.Quad),
            value = searchText,
            onValueChange = { searchText = it },
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(R.string.recipe_search_hilt),
                    style = MaterialTheme.typography.labelMedium,
                )
            },
        )

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
                            .padding(all = Padding.Double),
                        state = itemState,
                    )
                }
            }
        }
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
            ),
            onSearchTextChanged = { },
        )
    }
}
