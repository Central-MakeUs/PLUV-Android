package com.cmc15th.pluv.ui.feed

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.component.TopAppBar
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Gray600
import com.cmc15th.pluv.core.ui.component.PlaylistItem
import com.cmc15th.pluv.ui.feed.viewmodel.FeedUiEffect
import com.cmc15th.pluv.ui.feed.viewmodel.FeedUiEvent
import com.cmc15th.pluv.ui.feed.viewmodel.FeedViewModel

@Composable
fun SavedFeedScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    showSnackBar: (String) -> Unit = {},
    onBackClicked: () -> Unit = {},
    navigateToFeedDetail: () -> Unit = {},
    navigateToMigration: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Log.d("SAVEDFEED", "SavedFeedScreen: $viewModel")
    LaunchedEffect(Unit) {
        viewModel.setEvent(FeedUiEvent.OnLoadSavedFeeds)

        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is FeedUiEffect.OnFailure -> {
                    showSnackBar(effect.message)
                }
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopAppBar(description = "저장한 플레이리스트", onBackClick = onBackClicked)

        if (uiState.allFeeds.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.empty_icon),
                        contentDescription = "빈 플레이리스트",
                        modifier = Modifier.size(130.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("저장된 플레이리스트가 없어요", style = Content1, color = Gray600)
                }
            }
        } else {
            LazyColumn {
                items(
                    items = uiState.allFeeds,
                    key = { feed -> feed.id }
                ) { feed ->
                    PlaylistItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.setEvent(FeedUiEvent.SelectFeed(feed.id))
                                navigateToFeedDetail()
                            }
                            .padding(horizontal = 24.dp, vertical = 10.dp),
                        thumbNailUrl = feed.thumbNailUrl,
                        playlistName = feed.title,
                        totalMusicCount = feed.totalSongCount,
                        lastUpdateDate = feed.transferredAt
                    )
                }
            }
        }
    }
}