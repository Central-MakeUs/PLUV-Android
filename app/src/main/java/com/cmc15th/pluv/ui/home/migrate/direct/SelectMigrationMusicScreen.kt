package com.cmc15th.pluv.ui.home.migrate.direct

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.component.PlaylistCard
import com.cmc15th.pluv.core.designsystem.component.TopBarWithProgress
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.SelectedAppName
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.core.ui.component.MusicItem
import com.cmc15th.pluv.ui.home.migrate.component.PreviousOrMigrateButton
import com.cmc15th.pluv.ui.home.migrate.component.SourceToDestinationText

@Composable
fun SelectMigrationMusicScreen(
    modifier: Modifier = Modifier,
    currentStep: Int = 0,
    totalStep: Int = 0,
    onCloseClick: () -> Unit = {},
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToSelectPlaylist: () -> Unit,
    navigateToExecuteMigrationScreen: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedMusics by viewModel.selectedMusics

    Scaffold(
        topBar = {
            TopBarWithProgress(
                totalStep = totalStep,
                currentStep = currentStep,
                onCloseClick = {
                    onCloseClick()
                }
            )
        },
        bottomBar = {
            PreviousOrMigrateButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
                    .size(58.dp),
                isNextButtonEnabled = true,
                onPreviousClick = { navigateToSelectPlaylist() },
                onMigrateClick = { }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            SourceToDestinationText(
                uiState.selectedSourceApp.appName,
                uiState.selectedDestinationApp.appName
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "플레이리스트의 음악이\n일치하는지 확인해 주세요", style = Title1)
            Spacer(modifier = Modifier.size(28.dp))
            PlaylistInfo(
                appName = uiState.selectedSourceApp.appName,
                playlistName = "여유로운 오후의 취향 저격 팝",
                totalSongCount = 10
            )

            Spacer(modifier = Modifier.size(70.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    uiState.allMusics,
                    key = { music -> music.id }
                ) { music ->
                    MusicItem(
                        isChecked = selectedMusics.contains(music.id),
                        imageUrl = music.thumbNailUrl,
                        musicName = music.title,
                        artistName = music.artist,
                        onCheckedChange = { _ ->
                            viewModel.setEvent(DirectMigrationUiEvent.SelectMusic(music.id))
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun PlaylistInfo(
    appName: String,
    playlistName: String,
    totalSongCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        PlaylistCard(
            imageUrl = "https://picsum.photos/140",
            modifier = Modifier.size(86.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            Text(text = appName, style = Content2)

            Spacer(modifier = Modifier.size(8.dp))

            Row {
                Icon(
                    painter = painterResource(id = R.drawable.menu_04),
                    contentDescription = "playlist Name Icon",
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text(text = playlistName, style = Title1)
            }

            Spacer(modifier = Modifier.size(20.dp))

            Text(text = "총 ${totalSongCount}곡", style = SelectedAppName)
        }
    }
}

@Preview
@Composable
fun SelectMigrationMusicScreenPreview() {
    SelectMigrationMusicScreen(
        navigateToSelectPlaylist = {},
        navigateToExecuteMigrationScreen = {}
    )
}