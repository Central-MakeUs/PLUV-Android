package com.cmc15th.pluv.feature.migrate.direct

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.core.designsystem.component.ExitDialog
import com.cmc15th.pluv.core.designsystem.component.LoadingDialog
import com.cmc15th.pluv.core.designsystem.component.PlaylistCard
import com.cmc15th.pluv.core.designsystem.component.TopBarWithProgress
import com.cmc15th.pluv.core.designsystem.theme.Content0
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Gray600
import com.cmc15th.pluv.core.designsystem.theme.PrimaryDefault
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.feature.migrate.common.component.PreviousOrMigrateButton
import com.cmc15th.pluv.feature.migrate.viewmodel.DirectMigrationUiEffect
import com.cmc15th.pluv.feature.migrate.viewmodel.DirectMigrationUiEvent
import com.cmc15th.pluv.feature.migrate.viewmodel.DirectMigrationViewModel

@Composable
fun SelectMigratePlaylistScreen(
    modifier: Modifier = Modifier,
    currentStep: Int = 0,
    totalStep: Int = 0,
    onCloseClick: () -> Unit = {},
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToDisplayMigrationPath: () -> Unit,
    navigateToSelectMigrationMusic: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                DirectMigrationUiEffect.OnFetchMusicSuccess -> {
                    navigateToSelectMigrationMusic()
                }

                DirectMigrationUiEffect.OnFailure -> {
                    //TODO 에러 표시
                }
                else -> {}
            }
        }
    }

    if (uiState.exitDialogState) {
        ExitDialog(
            onDismissRequest = {
                viewModel.setEvent(DirectMigrationUiEvent.ShowExitMigrationDialog)
            },
            onConfirmClicked = {
                viewModel.setEvent(DirectMigrationUiEvent.ShowExitMigrationDialog)
                onCloseClick()
            }
        )
    }

    if (uiState.isLoading) {
        LoadingDialog(
            icon = {
                Icon(
                    painterResource(id = R.drawable.musicicon),
                    contentDescription = "fetch playlist loading",
                    tint = PrimaryDefault,
                    modifier = Modifier.size(50.dp)
                )
            },
            description = "음악을\n불러오는 중이에요!",
            onDismissRequest = {}
        )
    }

    Scaffold(
        topBar = {
            TopBarWithProgress(
                totalStep = totalStep,
                currentStep = currentStep,
                sourceApp = uiState.selectedSourceApp.name,
                destinationApp = uiState.selectedDestinationApp.name,
                onCloseClick = {
                    viewModel.setEvent(DirectMigrationUiEvent.ShowExitMigrationDialog)
                }
            )
        },
        bottomBar = {
            PreviousOrMigrateButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
                    .size(58.dp),
                isNextButtonEnabled = uiState.selectedPlaylist.id.isNotEmpty(),
                onPreviousClick = { navigateToDisplayMigrationPath() },
                onMigrateClick = { viewModel.setEvent(DirectMigrationUiEvent.FetchMusicsByPlaylist) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {

            Text(text = "어떤 플레이리스트를 옮길까요?", style = Title1)
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "최대 1개 선택",
                style = Content1,
                color = Gray600,
                textAlign = TextAlign.Start,
            )

            Spacer(modifier = Modifier.size(24.dp))

            if (uiState.allPlaylists.isEmpty()) {
                EmptyPlaylistIcon()
            } else {
                PlaylistColumn(
                    playlists = uiState.allPlaylists,
                    selectedPlaylist = uiState.selectedPlaylist,
                    modifier = Modifier.fillMaxSize().padding(15.dp),
                    onPlaylistSelect = {
                        viewModel.setEvent(DirectMigrationUiEvent.SelectPlaylist(it))
                    }
                )
            }
        }
    }
}

@Composable
fun PlaylistColumn(
    playlists: List<Playlist>,
    selectedPlaylist: Playlist,
    modifier: Modifier = Modifier,
    onPlaylistSelect: (Playlist) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
    ) {
        items(
            playlists,
            key = { playlist -> playlist.id }
        ) { playlist ->
            Column {
                PlaylistCard(
                    modifier = Modifier
                        .size(140.dp)
                        .border(
                            if (playlist.id == selectedPlaylist.id) 2.4.dp else 0.dp,
                            PrimaryDefault,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onPlaylistSelect(playlist) },
                    imageUrl = playlist.thumbNailUrl,
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = playlist.name, style = Content0)
            }
        }
    }
}

@Composable
fun EmptyPlaylistIcon(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painterResource(id = R.drawable.empty_icon),
            contentDescription = "empty icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(130.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "플레이리스트가 없어요", style = Content1, color = Gray600)
    }
}

@Preview
@Composable
fun SelectMigratePlaylistScreenPreview() {
    SelectMigratePlaylistScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        navigateToDisplayMigrationPath = {},
        navigateToSelectMigrationMusic = {}
    )
}

