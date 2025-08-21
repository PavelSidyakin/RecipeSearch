package com.recipebook.uikit.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import coil3.compose.AsyncImage

/**
 * Displays image with the provided URL.
 *
 * @param imageUrl The URL of the image.
 * @param modifier Optional [Modifier].
 * @param contentScale Optional [ContentScale].
 */
@Composable
fun RemoteImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    when (LocalInspectionMode.current) {
        true -> Box(
            modifier = modifier.background(MaterialTheme.colorScheme.onSecondaryContainer),
        )

        false -> AsyncImage(
            modifier = modifier,
            model = imageUrl,
            contentScale = contentScale,
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceDim),
            fallback = ColorPainter(MaterialTheme.colorScheme.surfaceDim),
            contentDescription = null,
        )
    }
}
