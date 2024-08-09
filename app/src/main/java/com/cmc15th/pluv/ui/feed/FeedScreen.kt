package com.cmc15th.pluv.ui.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.core.designsystem.component.PlaylistCard
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.Title3
import com.cmc15th.pluv.core.designsystem.theme.Title4

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "최신 플레이리스트", style = Title3, color = Gray800)

        Spacer(modifier = Modifier.height(16.dp))

//        LazyVerticalGrid(columns = 2) {
//
//        }
        PlaylistInfo(
            modifier = Modifier.fillMaxWidth(),
            imageUrl = "https://picsum.photos/200/300",
            title = "Playlist Title",
            musicNames = listOf("Music1", "Music2", "Music3", "Music4", "Music5", "Music6", "Music7", "Music8", "Music9", "Music10"),
            userName = "User Name"
        )
    }
}

@Composable
fun PlaylistInfo(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    musicNames: List<String>,
    userName: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlaylistCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(172.dp),
            imageUrl = imageUrl
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            style = Title4,
            color = Gray800,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = userName, style = Content2, color = Gray800)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = musicNames.joinToString(", "),
            style = Content2,
            color = Gray800,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}