package com.cmc15th.pluv.core.designsystem.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PlaylistCard(
    imageUrl: String,
    shape: RoundedCornerShape = RoundedCornerShape(2.dp),
    modifier: Modifier = Modifier
) {
    Card(
        shape = shape,
    ) {
        AsyncImage(
            modifier = modifier,
            model = imageUrl,
            contentDescription = "Playlist Cover",
            contentScale = ContentScale.Crop
        )
    }
}