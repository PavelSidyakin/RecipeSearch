package com.recipebook.viewedrecipes.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.recipebook.strings.R
import com.recipebook.uikit.size.Padding
import com.recipebook.uikit.theme.RecipeSearchTheme
import com.recipebook.uikit.widgets.RemoteImage
import com.recipebook.uikit.widgets.TextWithMaxLines
import com.recipebook.viewedrecipes.presentation.model.ViewedRecipesItemState

private val RecipeImageSize = 64.dp

private const val NAME_MAX_LINES = 2
private const val DESCRIPTION_MAX_LINES = 3

@Composable
internal fun ViewedRecipeItem(
    state: ViewedRecipesItemState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Padding.Quad),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Padding.Double),
                contentAlignment = Alignment.Center,
            ) {
                RemoteImage(
                    modifier = Modifier
                        .size(RecipeImageSize),
                    imageUrl = state.imageUrl,
                )
                if (state.isFavorite) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.BottomEnd),
                        imageVector = Icons.Filled.Favorite,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null,
                    )
                }
            }
            TextWithMaxLines(
                text = state.name,
                style = MaterialTheme.typography.titleLarge,
                maxLines = NAME_MAX_LINES,
            )
            Text(
                modifier = Modifier.padding(vertical = Padding.Double),
                text = stringResource(R.string.recipe_search_price, state.price),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            TextWithMaxLines(
                text = state.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = DESCRIPTION_MAX_LINES,
            )
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(fontScale = 2.5f)
private fun ViewedRecipeItemPreview(
    @PreviewParameter(ViewedRecipeItemPreviewParameterProvider::class)
    state: ViewedRecipesItemState,
) {
    RecipeSearchTheme {
        ViewedRecipeItem(
            modifier = Modifier.wrapContentSize(),
            state = state,
        )
    }
}

private class ViewedRecipeItemPreviewParameterProvider : PreviewParameterProvider<ViewedRecipesItemState> {
    private val defaultState = ViewedRecipesItemState(
        recipeId = 0,
        imageUrl = "https://xxxx",
        name = "Name Name Name",
        description = "Description Description Description Description",
        price = 80.5f,
        isFavorite = false,
    )
    override val values: Sequence<ViewedRecipesItemState> = sequenceOf(
        defaultState,
        defaultState.copy(isFavorite = true),
    )
}