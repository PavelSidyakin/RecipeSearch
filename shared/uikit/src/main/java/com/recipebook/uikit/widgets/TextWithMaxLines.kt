package com.recipebook.uikit.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

/**
 * Displays multiline text with fixed height corresponding to the max lines.
 *
 * @param text The text to display.
 * @param maxLines The max lines for the text.
 * @param style The [TextStyle].
 * @param modifier Optional [Modifier].
 */
@Composable
fun TextWithMaxLines(
    text: String,
    maxLines: Int,
    style: TextStyle,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Text(
            style = style,
            maxLines = maxLines,
            text = "\n".repeat(maxLines),
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
