package com.cmc15th.pluv.ui.home.migrate.direct

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.component.ExitDialog
import com.cmc15th.pluv.core.designsystem.component.LoadingDialog
import com.cmc15th.pluv.core.designsystem.component.PlaylistCard
import com.cmc15th.pluv.core.designsystem.component.TopBarWithProgress
import com.cmc15th.pluv.core.designsystem.theme.Content0
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray600
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.core.ui.component.MusicItem
import com.cmc15th.pluv.core.ui.component.MusicsHeader
import com.cmc15th.pluv.domain.model.PlayListApp
import com.cmc15th.pluv.ui.common.contract.GoogleApiContract
import com.cmc15th.pluv.ui.common.contract.SpotifyAuthContract
import com.cmc15th.pluv.ui.home.migrate.common.component.PreviousOrMigrateButton
import kotlinx.coroutines.delay

private const val DialogDuration = 800L

@Composable
fun SelectMigrationMusicScreen(
    modifier: Modifier = Modifier,
    currentStep: Int = 0,
    totalStep: Int = 0,
    onShowSnackBar: (String) -> Unit = {},
    onCloseClick: () -> Unit = {},
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToSelectPlaylist: () -> Unit,
    navigateToSelectSimilarMusic: () -> Unit = {},
    navigateToShowNotFoundMusic: () -> Unit = {},
    navigateToExecuteMigrationScreen: () -> Unit
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

    var dialogVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var dialogDescription by rememberSaveable {
        mutableStateOf("")
    }


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

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is DirectMigrationUiEffect.OnValidateMusic -> {
                    if (effect.needValidate) {
                        dialogDescription = "앗, 찾을 수 없는 곡이\n몇 개 있네요!"
                        dialogVisible = true
                        delay(DialogDuration)
                        dialogVisible = false

                        if (uiState.similarMusics.isNotEmpty()) {
                            navigateToSelectSimilarMusic()
                        }

                        if (uiState.similarMusics.isEmpty() && uiState.notFoundMusics.isNotEmpty()) {
                            navigateToShowNotFoundMusic()
                        }

                    } else {
                        dialogDescription = "플레이리스트의\n모든 음악을 찾았어요!"
                        dialogVisible = true
                        delay(DialogDuration)
                        dialogVisible = false
                    }
                }

                is DirectMigrationUiEffect.OnLoginSuccess -> {
                    viewModel.setEvent(DirectMigrationUiEvent.OnDestinationLoginSuccess)
                }

                is DirectMigrationUiEffect.OnFailure -> {
                    //TODO 에러 표시
                }

                else -> {}
            }
        }
    }

    val dialogIcon: @Composable () -> Unit = when {
        uiState.isLoading -> {
            {
                Icon(
                    painterResource(id = R.drawable.findicon),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.Unspecified
                )
            }
        }

        dialogVisible -> {
            {
                Icon(
                    painterResource(id = R.drawable.warningicon),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.Unspecified
                )
            }
        }

        else -> {
            {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.Unspecified
                )
            }
        }
    }

    if (uiState.isLoading || dialogVisible) {
        LoadingDialog(
            icon = { dialogIcon() },
            description = if (uiState.isLoading) "음악을\n찾는 중이에요!" else dialogDescription,
            progressVisible = uiState.isLoading,
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
                isNextButtonEnabled = uiState.selectedSourceMusics.isNotEmpty(),
                onPreviousClick = { navigateToSelectPlaylist() },
                onMigrateClick = {
                    when (uiState.selectedDestinationApp) {
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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 28.dp)
            ) {

                Text(text = "플레이리스트의 음악이\n일치하는지 확인해 주세요", style = Title1)

                Spacer(modifier = Modifier.size(28.dp))

                PlaylistInfo(
                    imageUrl = uiState.selectedPlaylist.thumbNailUrl,
                    appName = uiState.selectedSourceApp.appName,
                    playlistName = uiState.selectedPlaylist.name,
                    totalSongCount = 10
                )

                Spacer(modifier = Modifier.size(70.dp))

                MusicsHeader(
                    modifier = Modifier.fillMaxWidth(),
                    selectedMusicCount = uiState.selectedSourceMusics.size,
                    isSelectedAll = uiState.selectedSourceMusics.size == uiState.allSourceMusics.size,
                    onAllSelectedClick = { isSelectedAll ->
                        viewModel.setEvent(
                            DirectMigrationUiEvent.SelectAllSourceMusic(
                                isSelectedAll
                            )
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.size(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    uiState.allSourceMusics,
                ) { music ->
                    MusicItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        isChecked = uiState.selectedSourceMusics.contains(music),
                        thumbNailContent = {
                            MusicThumbNail(
                                imageUrl = music.thumbNailUrl,
                                modifier = Modifier.size(50.dp)
                            )
                        },
                        musicName = music.title,
                        artistName = music.artistName,
                        onCheckedChange = { _ ->
                            viewModel.setEvent(DirectMigrationUiEvent.SelectSourceMusic(music))
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun PlaylistInfo(
    imageUrl: String,
    appName: String,
    playlistName: String,
    totalSongCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        PlaylistCard(
            imageUrl = imageUrl,
            modifier = Modifier.size(86.dp),
            shape = RoundedCornerShape(8.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.menu_04),
                    contentDescription = "playlist Name Icon",
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .size(20.dp)
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text(text = playlistName, style = Content0)
            }

            Spacer(modifier = Modifier.size(20.dp))

            Row {
                Text(text = appName, style = Content2, color = Gray600)
                Spacer(modifier = Modifier.size(6.dp))
                Text(text = "총 ${totalSongCount}곡", style = Content2, color = Gray600)
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
fun SelectMigrationMusicScreenPreview() {
    SelectMigrationMusicScreen(
        navigateToSelectPlaylist = {},
        navigateToExecuteMigrationScreen = {}
    )
}