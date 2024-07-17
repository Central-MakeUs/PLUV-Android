package com.cmc15th.pluv.ui.home.migrate.direct

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.component.LoadingDialog
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.core.designsystem.theme.Title4
import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.ui.home.migrate.component.PreviousOrMigrateButton
import com.cmc15th.pluv.ui.home.migrate.component.SourceToDestinationText

@Composable
fun SelectMigratePlaylistScreen(
    modifier: Modifier = Modifier,
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToDisplayMigrationPath: () -> Unit,
    navigateToSelectMigrationMusic: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                DirectMigrationUiEffect.onSuccess -> {
                    navigateToSelectMigrationMusic()
                }

                DirectMigrationUiEffect.onFailure -> {
                    //TODO 에러 표시
                }
            }
        }
    }

    if (uiState.isLoading) {
        LoadingDialog(
            icon = { /*TODO*/ },
            description = "음악을\n불러오는 중이에요!",
            onDismissRequest = {}
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        SourceToDestinationText(
            sourceApp = uiState.selectedSourceApp.appName,
            destinationApp = uiState.selectedDestinationApp.appName
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "옮길 플레이리스트를 선택 해주세요", style = Title1)
        Spacer(modifier = Modifier.size(28.dp))
        Text(
            text = "최대 1개",
            style = Content2,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
        PlaylistColumn(
            playlists = uiState.allPlaylists,
            selectedPlaylist = uiState.selectedPlaylist,
            modifier = Modifier.padding(15.dp),
            onPlaylistSelect = {}
        )
        Spacer(modifier = Modifier.weight(1f))
        PreviousOrMigrateButton(
            modifier = Modifier.size(58.dp),
//            isNextButtonEnabled = uiState.selectedPlaylist != -1L,
            isNextButtonEnabled = true, // FIXME
            onPreviousClick = { navigateToDisplayMigrationPath() },
            onMigrateClick = { viewModel.setEvent(DirectMigrationUiEvent.SelectPlaylist) }
        )
    }
}

@Composable
fun PlaylistColumn(
    playlists: List<Playlist>,
    selectedPlaylist: Long,
    modifier: Modifier = Modifier,
    onPlaylistSelect: (Long) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
    ) {
        items(6) { index ->
            Image(
                painter = painterResource(id = R.drawable.playlistex),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
//                    .border(
//                        if (playlist.id == selectedPlaylist) 2.dp else 0.dp,
//                        colorResource(id = R.color.destination_app_title_color)
//                    )
//                    .clickable { onPlaylistSelect(playlist.id) }
            )
//            PlaylistCard(imageUrl = "")
        }
    }
}

@Composable
fun SelectPlaylistText() {
    Text(text = "플레이리스트 선택", style = Title4)
}

@Preview
@Composable
fun SelectMigratePlaylistScreenPreview() {
    SelectMigratePlaylistScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        viewModel = DirectMigrationViewModel(),
        navigateToDisplayMigrationPath = {},
        navigateToSelectMigrationMusic = {}
    )
}

