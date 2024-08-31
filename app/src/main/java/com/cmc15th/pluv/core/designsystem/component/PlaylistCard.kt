package com.cmc15th.pluv.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cmc15th.pluv.R

@Composable
fun PlaylistCard(
    imageUrl: String,
    shape: RoundedCornerShape = RoundedCornerShape(2.dp),
    modifier: Modifier = Modifier
) {
    Card(
        shape = shape,
        modifier = modifier
    ) {
        if (imageUrl.isBlank()) {
            Image(
                painter = painterResource(id = R.drawable.default_music),
                contentDescription = "defaultMusic",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imageUrl,
                contentDescription = "Playlist Cover",
                contentScale = ContentScale.Crop
            )
        }
    }
}