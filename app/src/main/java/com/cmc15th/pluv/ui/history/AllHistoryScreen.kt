package com.cmc15th.pluv.ui.history

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
import com.cmc15th.pluv.ui.history.viewmodel.HistoryUiEvent
import com.cmc15th.pluv.ui.history.viewmodel.HistoryViewModel

@Composable
fun AllHistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    navigateToHistoryDetail: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setEvent(HistoryUiEvent.OnLoadHistories)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        TopAppBar(description = "최근 옮긴 항목", onBackClick = onBackClicked)

        LazyColumn {
            items(
                items = uiState.histories,
                key = { history -> history.id }
            ) { history ->
                PlaylistItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.setEvent(HistoryUiEvent.OnHistoryClicked(history.id))
                            navigateToHistoryDetail()
                        }
                        .padding(horizontal = 24.dp, vertical = 10.dp),
                    thumbNailUrl = history.imageUrl,
                    playlistName = history.title,
                    totalMusicCount = history.transferredSongCount,
                    lastUpdateDate = history.transferredDate
                )
            }
        }
    }
}