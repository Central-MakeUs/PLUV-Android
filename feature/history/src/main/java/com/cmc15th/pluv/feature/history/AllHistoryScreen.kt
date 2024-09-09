package com.cmc15th.pluv.feature.history

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
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.core.designsystem.component.TopAppBar
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Gray600
import com.cmc15th.pluv.core.ui.component.PlaylistItem
import com.cmc15th.pluv.feature.history.viewmodel.HistoryUiEvent
import com.cmc15th.pluv.feature.history.viewmodel.HistoryViewModel

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


        if (uiState.histories.isEmpty()) {
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
                    Text("최근 옮긴 항목이 없어요", style = Content1, color = Gray600)
                }
            }
        } else {
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
}