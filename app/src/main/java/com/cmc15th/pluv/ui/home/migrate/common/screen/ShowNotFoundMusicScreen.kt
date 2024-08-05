package com.cmc15th.pluv.ui.home.migrate.common.screen

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.core.designsystem.component.PlaylistCard
import com.cmc15th.pluv.core.designsystem.component.TopBarWithProgress
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.core.ui.component.MusicItem
import com.cmc15th.pluv.ui.home.migrate.common.component.PreviousOrMigrateButton
import com.cmc15th.pluv.ui.home.migrate.common.component.SourceToDestinationText
import com.cmc15th.pluv.ui.home.migrate.direct.DirectMigrationViewModel

@Composable
fun ShowNotFoundMusicScreen(
    modifier: Modifier = Modifier,
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    currentStep: Int = 0,
    totalStep: Int = 0,
    onCloseClick: () -> Unit = {},
    navigateToPrevious: () -> Unit = {},
    executeMigration: () -> Unit = {}
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

                Text(text = "${uiState.selectedDestinationApp}에서\n찾을 수 없는 음악이에요", style = Title1)

                Spacer(modifier = Modifier.size(28.dp))
                
                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = "${uiState.notFoundMusics.size}곡",
                    style = Content2
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    uiState.notFoundMusics
                ) { music ->
                    MusicItem(
                        thumbNailContent = {
                            MusicThumbNail(
                                imageUrl = music.thumbNailUrl
                            )
                        },
                        musicName = music.title,
                        artistName = music.artistName
                    )
                }
            }
        }
    }
}

@Composable
fun MusicThumbNail(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    PlaylistCard(
        imageUrl = imageUrl,
        modifier = modifier
    )
}

@Preview
@Composable
fun ShowNotFoundMusicScreenPreview() {
    ShowNotFoundMusicScreen()
}