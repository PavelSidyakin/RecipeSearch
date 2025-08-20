package com.recipebook.recipedetails.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recipebook.recipedetails.presentation.model.ErrorType
import com.recipebook.recipedetails.presentation.model.RecipeDetailsScreenState
import com.recipebook.recipedetails.presentation.viewmodel.RecipeDetailsViewModel
import com.recipebook.strings.R
import com.recipebook.uikit.size.Padding
import com.recipebook.uikit.theme.RecipeSearchTheme
import com.recipebook.uikit.widgets.RemoteImage

private val RecipeImageSize = 164.dp

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    modifier: Modifier = Modifier,
) {
    val viewModel: RecipeDetailsViewModel = hiltViewModel(
        key = recipeId.toString(),
        creationCallback = { factory: RecipeDetailsViewModel.Factory ->
            factory.create(recipeId)
        }
    )

    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        viewModel.onLaunch()
        onDispose { viewModel.onDispose() }
    }

    RecipeDetailsScreenImpl(
        modifier = modifier,
        state = state,
        onFavoriteClicked = viewModel::onFavoriteClicked,
    )
}

@Composable
private fun RecipeDetailsScreenImpl(
    state: RecipeDetailsScreenState,
    onFavoriteClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        when (state.errorType) {
            null -> DetailsList(
                modifier = Modifier.fillMaxWidth(),
                state = state,
            )

            else -> Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Padding.Double)
                    .padding(vertical = Padding.Quad),
                text = when (state.errorType) {
                    ErrorType.NETWORK -> stringResource(R.string.error_network_connection)
                    ErrorType.GENERAL -> stringResource(R.string.error_general)
                },
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun DetailsList(
    state: RecipeDetailsScreenState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.padding(Padding.Quad),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(all = Padding.Quad),
    ) {
        item {
            RemoteImage(
                modifier = Modifier
                    .padding(bottom = Padding.Quad)
                    .size(RecipeImageSize),
                imageUrl = state.recipeImageUrl,
            )
        }
        item {
            Text(
                text = state.recipeName,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
        }
        item {
            Text(
                text = stringResource(R.string.recipe_details_price, state.price),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
            )
        }
        item {
            Text(
                text = stringResource(R.string.recipe_details_ingredients_label),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
            )
        }
        item {
            Text(
                text = state.ingredients,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
            )
        }
        item {
            Text(
                text = stringResource(R.string.recipe_details_details),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
            )
        }
        item {
            Text(
                text = AnnotatedString.fromHtml(
                    htmlString = state.instructions,
                    linkStyles = TextLinkStyles(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                        ),
                    ),
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
            )
        }
        item {
            Text(
                text = stringResource(R.string.recipe_details_source),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
            )
        }
        item {
            Text(
                text = AnnotatedString.fromHtml(
                    htmlString = "<a href=\"${state.sourceWebsiteLink}\">${state.sourceWebsiteLink}</a>",
                    linkStyles = TextLinkStyles(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                        ),
                    ),
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF000000, showBackground = true)
@Preview(device = Devices.TABLET)
private fun RecipeDetailsScreenImplPreview(
    @PreviewParameter(RecipeDetailsScreenImplPreviewParameterProvider::class)
    state: RecipeDetailsScreenState,
) {
    RecipeSearchTheme {
        RecipeDetailsScreenImpl(
            modifier = Modifier.fillMaxSize(),
            state = state,
            onFavoriteClicked = { },
        )
    }
}

private class RecipeDetailsScreenImplPreviewParameterProvider : PreviewParameterProvider<RecipeDetailsScreenState> {
    private val defaultState = RecipeDetailsScreenState(
        recipeId = 0,
        recipeName = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
        recipeImageUrl = "https://img.spoonacular.com/recipes/716429-556x370.jpg",
        ingredients = "2 tbsp grated cheese (I used romano)\n1 tbsp butter",
        instructions = "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs might be a good recipe to " +
                "expand your main course repertoire. One portion of this dish contains approximately " +
                "<b>19g of protein </b>,  <b>20g of fat </b>, and a total of  <b>584 calories </b>. " +
                "For  <b>$1.63 per serving </b>, this recipe  <b>covers 23% </b> of your daily requirements " +
                "of vitamins and minerals. This recipe serves 2. It is brought to you by " +
                "fullbellysisters.blogspot.com. 209 people were glad they tried this recipe. " +
                "A mixture of scallions, salt and pepper, white wine, and a handful of other ingredients " +
                "are all it takes to make this recipe so scrumptious. From preparation to the plate, this " +
                "recipe takes approximately  <b>45 minutes </b>. All things considered, we decided this " +
                "recipe  <b>deserves a spoonacular score of 83% </b>. This score is awesome. " +
                "If you like this recipe, take a look at these similar recipes: " +
                "<a href=\"https://spoonacular.com/recipes/cauliflower-gratin-with-garlic-bre" +
                "adcrumbs-318375\">Cauliflower Gratin with Garlic Breadcrumbs</a>, " +
                "<a href=\"https://spoonacular.com/recipes/pasta-with-cauliflower-sausage-breadc" +
                "rumbs-30437\">Pasta With Cauliflower, Sausage, & Breadcrumbs</a>, and <a href=\"" +
                "https://spoonacular.com/recipes/pasta-with-roasted-cauliflower-parsley-and-breadcrum" +
                "bs-30738\">Pasta With Roasted Cauliflower, Parsley, And Breadcrumbs</a>.",
        sourceWebsiteLink = "http://fullbellysisters.blogspot.com/2012/06/pasta-with-gar" +
                "lic-scallions-cauliflower.html",
        isFavorite = false,
        price = 80.5f,
        errorType = null,
    )

    override val values: Sequence<RecipeDetailsScreenState> = sequenceOf(
        defaultState,
        defaultState.copy(errorType = ErrorType.NETWORK),
    )
}
