package com.cmc15th.pluv.core.designsystem.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PlaylistCard(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Playlist Cover",
            modifier = modifier
        )
    }
}