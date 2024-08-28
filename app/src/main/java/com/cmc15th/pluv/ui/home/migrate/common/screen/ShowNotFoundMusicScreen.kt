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
import com.cmc15th.pluv.core.designsystem.component.ExitDialog
import com.cmc15th.pluv.core.designsystem.component.PlaylistCard
import com.cmc15th.pluv.core.designsystem.component.TopBarWithProgress
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.core.ui.component.MusicItem
import com.cmc15th.pluv.ui.home.migrate.common.component.PreviousOrMigrateButton
import com.cmc15th.pluv.ui.home.migrate.direct.DirectMigrationUiEvent
import com.cmc15th.pluv.ui.home.migrate.direct.DirectMigrationViewModel

@Composable
fun ShowNotFoundMusicScreen(
    modifier: Modifier = Modifier,
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    currentStep: Int = 0,
    totalStep: Int = 0,
    onShowSnackBar: (String) -> Unit = {},
    onCloseClick: () -> Unit = {},
    navigateToPrevious: () -> Unit = {},
    navigateToMigrationProcess: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                isNextButtonEnabled = false,
                onPreviousClick = { },
                onMigrateClick = {
                    navigateToMigrationProcess()
                }
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
                Text(
                    text = "${uiState.selectedDestinationApp.appName}에서\n찾을 수 없는 음악이에요",
                    style = Title1
                )

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
                        modifier = Modifier.fillMaxWidth(),
                        isChecked = false,
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
) {
    PlaylistCard(
        imageUrl = imageUrl,
        modifier = Modifier.size(50.dp)
    )
}

@Preview
@Composable
fun ShowNotFoundMusicScreenPreview() {
    ShowNotFoundMusicScreen()
}