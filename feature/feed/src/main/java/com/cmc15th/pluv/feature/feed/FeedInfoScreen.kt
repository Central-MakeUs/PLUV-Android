package com.cmc15th.pluv.feature.feed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.core.designsystem.component.PLUVButton
import com.cmc15th.pluv.core.designsystem.theme.Gray200
import com.cmc15th.pluv.core.designsystem.theme.Title5
import com.cmc15th.pluv.core.ui.component.MusicItemWithIndexed
import com.cmc15th.pluv.core.ui.component.PlaylistInfo
import com.cmc15th.pluv.feature.feed.viewmodel.FeedUiEffect
import com.cmc15th.pluv.feature.feed.viewmodel.FeedUiEvent
import com.cmc15th.pluv.feature.feed.viewmodel.FeedViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedInfoScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    feedId: Long,
    onBackClick: () -> Unit = {},
    showSnackBar: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val feedInfo = uiState.feedInfo

    LaunchedEffect(Unit) {
        viewModel.setEvent(FeedUiEvent.SelectFeed(feedId))
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is FeedUiEffect.OnSaveSuccess -> showSnackBar(effect.message)
                is FeedUiEffect.OnFailure -> showSnackBar(effect.message)
            }
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.leftarrow),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onBackClick() }, contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                item {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(390.dp),
                        contentScale = ContentScale.Crop,
                        model = feedInfo.imageUrl.ifBlank {
                            R.drawable.default_music
                        },
                        contentDescription = "feed image"
                    )
                }
                stickyHeader {
                    PlaylistInfo(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(24.dp),
                        playlistName = feedInfo.title,
                        totalMusicCount = uiState.feedMusics.size,
                        lastUpdateDate = feedInfo.createdAt,
                        userName = feedInfo.creatorName,
                    )
                    Divider(modifier = Modifier.fillMaxWidth(), color = Gray200, thickness = 1.dp)
                }
                itemsIndexed(uiState.feedMusics) { index, music ->
                    MusicItemWithIndexed(
                        index = index,
                        imageUrl = music.thumbNailUrl,
                        musicName = music.title,
                        artistName = music.artistName
                    )
                }
            }

            Column {
                Divider(color = Color(0xFFF2F2F2), thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp)
                        .background(Color.White)
                        .padding(bottom = 34.dp, top = 10.dp, start = 24.dp, end = 24.dp)
                ) {
                    PLUVButton(
                        modifier = Modifier
                            .weight(0.33f)
                            .height(58.dp),
                        onClick = {
                            viewModel.setEvent(FeedUiEvent.ToggleBookmark(feedInfo.id))
                        },
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        content = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    painter =
                                    if (feedInfo.isBookMarked) painterResource(id = R.drawable.bookmark_state)
                                    else painterResource(id = R.drawable.unbookmark_state),
                                    contentDescription = "bookmark state",
                                    tint = Color.Unspecified
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text("저장", style = Title5)
                            }
                        }
                    )
//                    Spacer(modifier = Modifier.size(10.dp))
//                    PLUVButton(
//                        modifier = Modifier
//                            .weight(0.66f)
//                            .height(58.dp),
//                        onClick = { /*TODO*/ },
//                        containerColor = Color.Black,
//                        contentPadding = PaddingValues(vertical = 19.dp),
//                        contentColor = Color.White,
//                        content = {
//                            Text("플레이리스트 옮기기", style = Title5)
//                        }
//                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun FeedInfoScreenPreview() {
    FeedInfoScreen(feedId = 0, onBackClick = {})
}