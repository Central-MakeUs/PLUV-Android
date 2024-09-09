package com.cmc15th.pluv.feature.feed

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.core.designsystem.component.PlaylistCard
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray300
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.Title3
import com.cmc15th.pluv.core.designsystem.theme.Title4
import com.cmc15th.pluv.feature.feed.viewmodel.FeedUiEffect
import com.cmc15th.pluv.feature.feed.viewmodel.FeedUiEvent
import com.cmc15th.pluv.feature.feed.viewmodel.FeedViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel(),
    showSnackBar: (String) -> Unit = {},
    navigateToFeedInfo: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isLoading,
        onRefresh = { viewModel.setEvent(FeedUiEvent.OnLoadAllFeeds) }
    )

    LaunchedEffect(Unit) {
        viewModel.setEvent(FeedUiEvent.OnLoadAllFeeds)
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is FeedUiEffect.OnFailure -> {
                    showSnackBar(effect.message)
                }

                else -> {}
            }
        }
    }

    Box(
        modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .pullRefresh(pullRefreshState)
    ) {
        PullRefreshIndicator(
            refreshing = uiState.isLoading, state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "최신 플레이리스트",
                    style = Title3,
                    color = Gray800,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
            items(
                items = uiState.allFeeds,
                key = { feed -> feed.id }
            ) { feed ->
                FeedCard(
                    id = feed.id,
                    imageUrl = feed.thumbNailUrl,
                    title = feed.title,
                    musicNames = feed.artistNames.split(", "),
                    userName = feed.creatorName,
                    onClick = {
                        navigateToFeedInfo(feed.id)
                    },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun FeedCard(
    modifier: Modifier = Modifier,
    id: Long,
    imageUrl: String,
    title: String,
    musicNames: List<String>,
    userName: String,
    onClick: (Long) -> Unit = {}
) {
    Column(
        modifier = modifier,
    ) {
        Box {
            PlaylistCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(172.dp)
                    .border(0.5.dp, Gray300, shape = RoundedCornerShape(8.dp))
                    .clickable { onClick(id) },
                shape = RoundedCornerShape(8.dp),
                imageUrl = imageUrl
            )

            Icon(
                painter = painterResource(id = R.drawable.playbuttonpng),
                contentDescription = "playButton",
                modifier = Modifier
                    .padding(end = 15.dp, bottom = 15.dp)
                    .size(20.dp)
                    .align(Alignment.BottomEnd),
                tint = Color.Black.copy(alpha = 0.6f)
            )
        }


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
            text = musicNames.joinToString(" ,"),
            style = Content2,
            color = Color(0xFF5C5C5C),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}