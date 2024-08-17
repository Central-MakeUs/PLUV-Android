package com.cmc15th.pluv.ui.feed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray200
import com.cmc15th.pluv.core.designsystem.theme.Gray600
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.Title2
import com.cmc15th.pluv.core.designsystem.theme.Title5


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedInfoScreen(
    onBackClick: () -> Unit = {},
    showSnackBar: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.leftarrow),
                    modifier = Modifier.size(24.dp).clickable { onBackClick() }, contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(390.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
            stickyHeader {
                PlaylistInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(24.dp),
                    playlistName = "여유로운 오후 취향의 음악",
                    totalMusicCount = 10,
                    lastUpdateDate = "w",
                    userName = "커트",
                    onBookmarkClick = {
                        showSnackBar("플레이리스트를 저장했어요")
                    }
                )
                Divider(modifier = Modifier.fillMaxWidth(), color = Gray200, thickness = 1.dp)
            }
            items(
                List(100) { "Hello World!! $it" }
            ) {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun PlaylistInfo(
    modifier: Modifier = Modifier,
    playlistName: String,
    totalMusicCount: Int,
    lastUpdateDate: String,
    userName: String,
    isBookmarked: Boolean = true,
    onBookmarkClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.menu_04),
                    modifier = Modifier.size(20.dp),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = playlistName,
                    style = Title2,
                )
            }
            Icon(
                painter =
                if (isBookmarked) painterResource(id = R.drawable.bookmarked)
                else painterResource(id = R.drawable.unbookmark),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onBookmarkClick() }
            )
        }

        Row {
            Text(text = "총${totalMusicCount}곡", style = Content2, color = Gray600)
            Spacer(modifier = Modifier.size(6.dp))
            Text(text = lastUpdateDate, style = Content2, color = Gray600)
        }

        Spacer(modifier = Modifier.size(10.dp))

        Text(text = "공유한 사람: $userName", style = Title5, color = Gray800)
    }
}

@Composable
@Preview
fun FeedInfoScreenPreview() {
    FeedInfoScreen()
}