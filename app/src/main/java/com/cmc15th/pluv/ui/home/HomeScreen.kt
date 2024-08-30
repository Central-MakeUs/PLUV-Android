package com.cmc15th.pluv.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.component.PlaylistCard
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray200
import com.cmc15th.pluv.core.designsystem.theme.Gray400
import com.cmc15th.pluv.core.designsystem.theme.Gray500
import com.cmc15th.pluv.core.designsystem.theme.Gray600
import com.cmc15th.pluv.core.designsystem.theme.HomePurple
import com.cmc15th.pluv.core.designsystem.theme.HomeWhite
import com.cmc15th.pluv.core.designsystem.theme.PrimaryDefault
import com.cmc15th.pluv.core.designsystem.theme.Title2
import com.cmc15th.pluv.core.designsystem.theme.Title4
import com.cmc15th.pluv.ui.home.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDirectMigration: () -> Unit = {},
    navigateToScreenShotMigration: () -> Unit = {},
    navigateToHistory: () -> Unit = {},
    navigateToSavedFeed: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val playlistItems = listOf(
        "https://picsum.photos/140",
        "https://picsum.photos/140",
        "https://picsum.photos/140",
        "https://picsum.photos/140",
        "https://picsum.photos/140",
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Gray200)
            .verticalScroll(scrollState)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(listOf(HomePurple, HomeWhite)),
                )
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.size(45.dp))

            Icon(
                modifier = Modifier
                    .width(66.dp)
                    .height(24.dp),
                painter = painterResource(id = R.drawable.pluvlogo),
                tint = PrimaryDefault,
                contentDescription = "Pluv Logo"
            )

            Spacer(modifier = Modifier.size(19.dp))
            MigrationMethodColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                onDirectClick = {
                    navigateToDirectMigration()
                },
                onScreenShotClick = {
                    navigateToScreenShotMigration()
                }
            )
            Spacer(modifier = Modifier.size(50.dp))
        }

        HistoryArea(
            title = R.string.migrated_play_list,
            playlistItems = uiState.historiesThumbnailUrl,
            modifier = Modifier.fillMaxWidth(),
            onExpandClick = { navigateToHistory() }
        )

        Spacer(modifier = Modifier.size(12.dp))

        PlayListRow(
            playListItems = uiState.savedFeedsThumbnailUrl,
            description = "저장한 플레이리스트",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            onExpandClick = { navigateToSavedFeed() }
        )

//        Spacer(modifier = Modifier.size(45.dp))
//        PlayListRowArea(
//            context = context,
//            title = R.string.saved_play_list,
//            playlistItems = playlistItems,
//            modifier = Modifier.fillMaxWidth(),
//            onAddClick = {}
//        )
    }
}

@Composable
fun HistoryArea(
    @StringRes title: Int,
    playlistItems: List<String>,
    modifier: Modifier = Modifier,
    onExpandClick: () -> Unit
) {
    // Content inside the rounded container
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp))
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.size(24.dp))
        Icon(
            painter = painterResource(id = R.drawable.my_playlist),
            contentDescription = "MyPlaylist",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(108.dp)
                .height(20.dp),
            tint = Gray400
        )
        PlayListRow(
            playListItems = playlistItems,
            description = "최근 옮긴 항목",
            cardSize = 140.dp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            onExpandClick = onExpandClick
        )
    }
}

@Composable
fun PlayListRow(
    playListItems: List<String>,
    description: String,
    cardSize: Dp = 100.dp,
    modifier: Modifier = Modifier,
    onExpandClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = description, style = Title4, modifier = Modifier.padding(vertical = 24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onExpandClick() }
            ) {
                Text(
                    text = "전체보기",
                    style = Content2,
                    color = Gray600,
                )
                Icon(
                    painterResource(id = R.drawable.rightarrow),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardSize)
        ) {
            if (playListItems.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painterResource(id = R.drawable.empty_icon), modifier = Modifier.size(60.dp),
                        contentDescription = "Empty Icon",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "플레이리스트가 없어요",
                        style = Content2,
                    )
                }
            }
            else {
                LazyRow(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(playListItems) { playList ->
                        PlaylistCard(
                            imageUrl = playList,
                            modifier = Modifier
                                .size(cardSize),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(24.dp))
    }
}

//@Composable
//fun PlayListItem(
//    context: Context,
//    imageUrl: String,
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier = modifier.clip(shape = MaterialTheme.shapes.small)
//    ) {
//        AsyncImage(
//            model = ImageRequest.Builder(context = context)
//                .data(imageUrl)
//                .crossfade(true)
//                .build(),
//            contentDescription = "Migrated Play List Image",
//            modifier = Modifier.fillMaxSize()
//        )
//        Icon(
//            imageVector = Icons.Default.PlayArrow,
//            contentDescription = null,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(5.dp)
//        )
//    }
//}

@Composable
fun MigrationMethodColumn(
    modifier: Modifier = Modifier,
    onDirectClick: () -> Unit,
    onScreenShotClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        MigrationMethodItem(
            R.string.migration_direct,
            icon = R.drawable.direct_icon,
            onClick = onDirectClick,
            description = stringResource(id = R.string.migration_direct_description),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(12.dp))
        MigrationMethodItem(
            R.string.migration_screenshot,
            icon = R.drawable.screenshot_icon,
            onClick = onScreenShotClick,
            description = stringResource(id = R.string.migration_screenshot_description),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MigrationMethodItem(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(108.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .size(74.dp),
                painter = painterResource(id = icon),
                tint = Color.Unspecified,
                contentDescription = "Migration Method Icon"
            )

            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(id = text),
                    style = Title2,
                )
                Spacer(modifier = Modifier.size(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = description,
                        style = Content1,
                        color = Gray500
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Icon(
                        painterResource(id = R.drawable.rightarrow),
                        contentDescription = null,
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
        }
    }
}