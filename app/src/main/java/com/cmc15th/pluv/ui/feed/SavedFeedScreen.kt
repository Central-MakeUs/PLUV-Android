package com.cmc15th.pluv.ui.feed

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.core.designsystem.component.TopAppBar
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
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        TopAppBar(description = "저장한 플레이리스트", onBackClick = onBackClicked)

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