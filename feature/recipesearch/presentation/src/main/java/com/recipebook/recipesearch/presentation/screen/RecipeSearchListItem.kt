package com.recipebook.recipesearch.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recipebook.recipesearch.presentation.model.RecipeSearchListItemState
import com.recipebook.uikit.size.Padding
import com.recipebook.uikit.theme.RecipeSearchTheme
import com.recipebook.uikit.widgets.RemoteImage
import com.recipebook.strings.R

private val RecipeImageSize = 64.dp

private const val NAME_MAX_LINES = 2
private const val DESCRIPTION_MAX_LINES = 3

@Composable
internal fun RecipeSearchListItem(
    state: RecipeSearchListItemState,
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
            RemoteImage(
                modifier = Modifier
                    .padding(bottom = Padding.Double)
                    .size(RecipeImageSize),
                imageUrl = state.imageUrl,
            )
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
private fun TextWithMaxLines(
    text: String,
    maxLines: Int,
    style: TextStyle,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Text(
            style = style,
            maxLines = maxLines,
            text = "\n".repeat(DESCRIPTION_MAX_LINES),
        )
        Text(
            text = AnnotatedString.fromHtml(text),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = maxLines,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(fontScale = 2.5f)
private fun RecipeSearchListItemPreview() {
    RecipeSearchTheme {
        RecipeSearchListItem(
            modifier = Modifier.wrapContentSize(),
            state = RecipeSearchListItemState(
                recipeId = 0,
                imageUrl = "https://xxxx",
                name = "Name Name Name",
                description = "Description Description Description Description",
                price = 80.5f,
            )
        )
    }
}
