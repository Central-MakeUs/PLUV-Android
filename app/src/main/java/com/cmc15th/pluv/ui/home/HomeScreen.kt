package com.cmc15th.pluv.ui.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cmc15th.pluv.R

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val playlistItems = listOf(
        "https://picsum.photos/140",
        "https://picsum.photos/140",
        "https://picsum.photos/140",
        "https://picsum.photos/140",
        "https://picsum.photos/140",
    )

    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.app_logo_color)
        )
        Spacer(modifier = Modifier.size(19.dp))
        MigrationMethodColumn(
            modifier = Modifier
                .fillMaxWidth(),
            onDirectClick = {},
            onScreenShotClick = {}
        )
        Spacer(modifier = Modifier.size(50.dp))
        PlayListRowArea(
            context = context,
            title = R.string.migrated_play_list,
            playlistItems = playlistItems,
            modifier = Modifier.fillMaxWidth(),
            onAddClick = {}
        )
        Spacer(modifier = Modifier.size(45.dp))
        PlayListRowArea(
            context = context,
            title = R.string.saved_play_list,
            playlistItems = playlistItems,
            modifier = Modifier.fillMaxWidth(),
            onAddClick = {}
        )
    }
}

@Composable
fun PlayListRowArea(
    context: Context,
    @StringRes title: Int,
    playlistItems: List<String>,
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit
) {
    PlayListName(title, modifier = modifier, onAddClick = onAddClick)
    Spacer(modifier = Modifier.size(13.dp))
    PlayListRow(context = context, playListItems = playlistItems, modifier = modifier)
}

@Composable
fun PlayListName(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit
) {
    Box(modifier = modifier) {
        Text(
            text = stringResource(id = title),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterStart)
        )
        Text(
            text = stringResource(id = R.string.show_more),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { onAddClick() },
        )
    }
}

@Composable
fun PlayListRow(
    context: Context,
    playListItems: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(playListItems) { playList ->
            PlayListItem(
                context = context,
                imageUrl = playList,
                modifier = Modifier.size(140.dp)
            )
        }
    }
}

@Composable
fun PlayListItem(
    context: Context,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.clip(shape = MaterialTheme.shapes.small)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Migrated Play List Image",
            modifier = Modifier.fillMaxSize()
        )
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(5.dp)
        )
    }
}

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
            onClick = onDirectClick,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(12.dp))
        MigrationMethodItem(
            R.string.migration_screenshot,
            onClick = onScreenShotClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun MigrationMethodItem(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.height(80.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = modifier
                .clickable(onClick = onClick)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(id = R.drawable.grayplaceholder),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = stringResource(id = text),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 13.dp)
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.rightarrow),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp) // 아이콘 크기 조정
                )
            }
        }
    }
}