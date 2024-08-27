package com.cmc15th.pluv.ui.home.migrate.direct

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.component.ExitDialog
import com.cmc15th.pluv.core.designsystem.component.LoadingDialog
import com.cmc15th.pluv.core.designsystem.component.TopBarWithProgress
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.domain.model.PlayListApp
import com.cmc15th.pluv.ui.common.contract.GoogleApiContract
import com.cmc15th.pluv.ui.common.contract.SpotifyAuthContract
import com.cmc15th.pluv.ui.home.getAppNameRes
import com.cmc15th.pluv.ui.home.migrate.common.component.FetchPlaylistLoadingIcon
import com.cmc15th.pluv.ui.home.migrate.common.component.PreviousOrMigrateButton

@Composable
fun DisplayMigrationPathScreen(
    modifier: Modifier = Modifier,
    currentStep: Int = 0,
    totalStep: Int = 0,
    onCloseClick: () -> Unit = {},
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToSelectDestinationApp: () -> Unit,
    navigateToSelectPlaylist: () -> Unit = {}
) {
    val googleLoginResultLauncher = rememberLauncherForActivityResult(
        contract = GoogleApiContract()
    ) { task ->
        viewModel.setEvent(DirectMigrationUiEvent.GoogleLogin(task))
    }

    val spotifyLoginResultLauncher = rememberLauncherForActivityResult(
        contract = SpotifyAuthContract()
    ) { task ->
        viewModel.setEvent(DirectMigrationUiEvent.SpotifyLogin(task))
    }

    val uiState = viewModel.uiState.collectAsState()

    // 플레이리스트를 성공적으로 가져온 경우 플리 선택 화면으로 이동
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                DirectMigrationUiEffect.OnLoginSuccess -> {
                    viewModel.setEvent(DirectMigrationUiEvent.OnSourceLoginSuccess)
                }
                DirectMigrationUiEffect.OnFetchPlaylistSuccess -> {
                    navigateToSelectPlaylist()
                }

                DirectMigrationUiEffect.OnFailure -> {
                    //TODO 에러 표시
                }

                else -> {}
            }
        }
    }

    // 플레이리스트 목록 가져오는 중일 경우 Dialog 표시
    if (uiState.value.isLoading) {

        LoadingDialog(
            icon = {
                FetchPlaylistLoadingIcon()
            },
            description = "플레이리스트를\n불러오는 중이예요!",
            onDismissRequest = {}
        )
    }


    if (uiState.value.exitDialogState) {
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
                isNextButtonEnabled = true,
                onPreviousClick = { navigateToSelectDestinationApp() },
                onMigrateClick = {
                    when (
                        uiState.value.selectedSourceApp
                    ) {
                        PlayListApp.Spotify -> {
                            spotifyLoginResultLauncher.launch(1)
                        }

                        PlayListApp.YoutubeMusic -> {
                            googleLoginResultLauncher.launch(1)
                        }

                        else -> {}
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DestinationAppText(
                    title = uiState.value.selectedDestinationApp.getAppNameRes(),
                    modifier = Modifier.align(Alignment.Start),
                    textStyle = Title1
                )
                Spacer(modifier = Modifier.size(32.dp))
                SelectedAppItem(playListApp = uiState.value.selectedSourceApp)
                Spacer(modifier = Modifier.size(23.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SourceToDestinationDots()
                    SourceToDestinationDots()
                    SourceToDestinationDots()
                }
                Spacer(modifier = Modifier.size(23.dp))
                SelectedAppItem(playListApp = uiState.value.selectedDestinationApp)
            }
        }
    }
}

@Composable
fun DestinationAppText(
    @StringRes title: Int,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row {
            Text(
                text = stringResource(id = title),
                style = textStyle,
                color = colorResource(id = R.color.destination_app_title_color)
            )
            Text(
                text = "으로",
                style = textStyle
            )
        }
        Text(
            text = stringResource(id = R.string.ask_migrate_playlist),
            style = textStyle
        )
    }

}

@Composable
fun MigrateButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Text(
            text = "옮기기",
            fontSize = 16.sp,
        )
    }
}

@Preview
@Composable
fun DisplayMigrationPathPreview() {
    Scaffold(
        topBar = {
            TopBarWithProgress(totalStep = 5, currentStep = 1, onCloseClick = {})
        },
        bottomBar = {
            PreviousOrMigrateButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
                    .size(58.dp),
                isNextButtonEnabled = true,
                onPreviousClick = { },
                onMigrateClick = {}
            )
        }
    ) { paddingValues ->
        DisplayMigrationPathScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navigateToSelectDestinationApp = {},
            onCloseClick = {}
        )
    }
}

