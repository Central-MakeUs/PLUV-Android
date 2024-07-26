package com.cmc15th.pluv.ui.home.migrate.common.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.core.designsystem.component.TopBarWithProgress
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.core.ui.component.MusicItem
import com.cmc15th.pluv.core.ui.component.MusicsHeader
import com.cmc15th.pluv.core.ui.component.OriginalMusicItem
import com.cmc15th.pluv.ui.home.migrate.common.component.PreviousOrMigrateButton
import com.cmc15th.pluv.ui.home.migrate.common.component.SourceToDestinationText
import com.cmc15th.pluv.ui.home.migrate.direct.DirectMigrationUiEvent
import com.cmc15th.pluv.ui.home.migrate.direct.DirectMigrationViewModel

@Composable
fun SelectSimilarMusicScreen(
    modifier: Modifier = Modifier,
    currentStep: Int = 0,
    totalStep: Int = 0,
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    onCloseClick: () -> Unit = {},
    navigateToSelectMigrationMusic: () -> Unit = {},
    navigateToShowNotFoundMusic: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                onPreviousClick = { },
                onMigrateClick = { }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 28.dp)
            ) {
                SourceToDestinationText(
                    sourceApp = uiState.selectedSourceApp.appName,
                    destinationApp = uiState.selectedDestinationApp.appName
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(text = "가장 유사한 항목을 옮길까요?", style = Title1)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "일부 정보만 일치하는 음악이에요.", style = Content1, color = Color(0xFF8E8E8E))

                Spacer(modifier = Modifier.size(28.dp))
                MusicsHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    selectedMusicCount = uiState.selectedSimilarMusics.size,
                    isSelectedAll = uiState.selectedSimilarMusics.size == uiState.similarMusics.size,
                    onAllSelectedClick = {
                        viewModel.setEvent(DirectMigrationUiEvent.SelectAllValidateMusic(it))
                    }
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    uiState.similarMusics,
                    key = { it.sourceMusic.isrcCode }
                ) { music ->
                    MusicWithSimilarMusic(
                        modifier = Modifier.fillMaxWidth(),
                        isChecked = uiState.selectedSimilarMusics.contains(music),
                        imageUrl = music.sourceMusic.thumbNailUrl,
                        musicName = music.sourceMusic.title,
                        artistName = music.sourceMusic.artistName,
                        originalMusicImageUrl = music.destinationMusic.thumbNailUrl,
                        originalMusicName = music.destinationMusic.title,
                        originalArtistName = music.destinationMusic.artistName,
                        onCheckedChange = {
                            viewModel.setEvent(DirectMigrationUiEvent.SelectSimilarMusic(music))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MusicWithSimilarMusic(
    modifier: Modifier = Modifier,
    isChecked: Boolean = true,
    imageUrl: String = "",
    musicName: String = "",
    artistName: String = "",
    originalMusicImageUrl: String = "",
    originalMusicName: String = "",
    originalArtistName: String = "",
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        MusicItem(
            isChecked = isChecked,
            imageUrl = imageUrl,
            musicName = musicName,
            artistName = artistName,
            onCheckedChange = {
                onCheckedChange(it)
            }
        )
        if (isChecked) {
            OriginalMusicItem(
                modifier = Modifier
                    .background(color = Color(0xFFCB84FF).copy(alpha = 0.08f))
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                imageUrl = originalMusicImageUrl,
                musicName = originalMusicName,
                artistName = originalArtistName
            )
        }
    }
}

@Composable
@Preview
fun SelectSimilarMusicScreenPreview() {
    SelectSimilarMusicScreen(
        currentStep = 1,
        totalStep = 3
    )
}