package com.cmc15th.pluv.ui.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.theme.Gray400
import com.cmc15th.pluv.core.designsystem.theme.PrimaryDefault
import com.cmc15th.pluv.core.designsystem.theme.Title5
import com.cmc15th.pluv.core.ui.component.MusicItemWithIndexed
import com.cmc15th.pluv.core.ui.component.PlaylistInfo
import com.cmc15th.pluv.ui.history.viewmodel.HistoryViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryDetailScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onBackClicked: () -> Unit = {},
) {
    val tabNames = listOf("옮긴 곡", "안 옮긴 곡")
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val historyDetail = uiState.selectedHistory

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val musicItems =
        if (selectedTabIndex == 0) uiState.transferSuccessMusics
        else uiState.transferFailMusics

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
                        .clickable { onBackClicked() }, contentDescription = null
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
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(390.dp),
                    contentScale = ContentScale.Crop,
                    model = historyDetail.imageUrl,
                    contentDescription = "feed image"
                )
            }
            stickyHeader {
                PlaylistInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(24.dp),
                    playlistName = historyDetail.title,
                    totalMusicCount = historyDetail.totalSongCount,
                    lastUpdateDate = "2024.02.02",
                )

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = PrimaryDefault
                        )
                    }
                ) {
                    tabNames.forEachIndexed { index, name ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = {
                                selectedTabIndex = index
                            }
                        ) {
                            Text(
                                text = name,
                                style = Title5,
                                color = if (selectedTabIndex == index) PrimaryDefault else Gray400,
                                modifier = Modifier.padding(vertical = 11.5.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            itemsIndexed(musicItems) { index, music ->
                MusicItemWithIndexed(
                    index = index,
                    imageUrl = music.imageUrl,
                    musicName = music.title,
                    artistName = music.artistNames
                )
            }
        }
    }
}

