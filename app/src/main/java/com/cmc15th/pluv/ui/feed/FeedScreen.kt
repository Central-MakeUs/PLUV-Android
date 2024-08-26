package com.cmc15th.pluv.ui.feed

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.component.PlaylistCard
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray300
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.Title3
import com.cmc15th.pluv.core.designsystem.theme.Title4
import com.cmc15th.pluv.ui.feed.viewmodel.FeedUiEffect
import com.cmc15th.pluv.ui.feed.viewmodel.FeedUiEvent
import com.cmc15th.pluv.ui.feed.viewmodel.FeedViewModel

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel(),
    showSnackBar: (String) -> Unit = {},
    navigateToFeedInfo: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setEvent(FeedUiEvent.OnLoadAllFeeds)
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is FeedUiEffect.OnFailure -> {
                     showSnackBar(effect.message)
                }
            }
        }
    }
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "최신 플레이리스트", style = Title3, color = Gray800)

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
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
                        viewModel.setEvent(FeedUiEvent.SelectFeed(feed.id))
                        navigateToFeedInfo()
                    }
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