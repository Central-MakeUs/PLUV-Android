package com.cmc15th.pluv.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.core.designsystem.component.PlaylistCard
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray600
import com.cmc15th.pluv.core.designsystem.theme.Title4
import com.cmc15th.pluv.core.ui.R

@Composable
fun PlaylistItem(
    modifier: Modifier = Modifier,
    thumbNailUrl: String,
    playlistName: String,
    totalMusicCount: Int,
    lastUpdateDate: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            PlaylistCard(
                imageUrl = thumbNailUrl,
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.size(74.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.playbuttonpng),
                contentDescription = "playButton",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(6.dp)
                    .size(14.dp),
                tint = Color.White.copy(alpha = 0.6f)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.menu_04),
                    modifier = Modifier.size(20.dp),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = playlistName,
                    style = Title4,
                    maxLines = 1,
                    overflow = Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                Text(text = "총 ${totalMusicCount}곡", style = Content2, color = Gray600)
                Spacer(modifier = Modifier.size(6.dp))
                Text(text = lastUpdateDate, style = Content2, color = Gray600)
            }
        }
    }
}