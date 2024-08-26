package com.cmc15th.pluv.ui.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import coil.compose.AsyncImage
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.theme.Gray400
import com.cmc15th.pluv.core.designsystem.theme.PrimaryDefault
import com.cmc15th.pluv.core.designsystem.theme.Title5
import com.cmc15th.pluv.core.ui.component.PlaylistInfo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryInfoScreen() {
    val tabNames = listOf("옮긴 곡", "안 옮긴 곡")
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
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
                        .clickable { }, contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            item {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(390.dp),
                    contentScale = ContentScale.Crop,
                    model = "https://picsum.photos/400/300",
                    contentDescription = "feed image"
                )
            }
            stickyHeader {
                PlaylistInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(24.dp),
                    playlistName = "",
                    totalMusicCount = 4,
                    lastUpdateDate = "", userName = "dwqe"
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
            }
            items(100) {
                Text(text = "Item $it")
            }
        }
    }
}
