package com.recipebook.uikit.widgets

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.recipebook.uikit.size.Padding
import com.recipebook.uikit.theme.RecipeSearchTheme

/**
 * Screen header.
 * Has text and a back button.
 *
 * @param title The title of the screen.
 * @param onBackButtonClicked The callback is called then the back button is clicked.
 * @param modifier Optional [Modifier].
 */
@Composable
fun ScreenHeader(
    title: String,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(Padding.Quad),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Padding.Double),
    ) {
        Icon(
            modifier = Modifier.clickable(
                onClick = onBackButtonClicked,
            ),
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, backgroundColor = 0xFF000000, showBackground = true)
private fun ScreenHeaderPreview() {
    RecipeSearchTheme {
        ScreenHeader(
            title = "title title title title title title ",
            onBackButtonClicked = { },
        )
    }
}