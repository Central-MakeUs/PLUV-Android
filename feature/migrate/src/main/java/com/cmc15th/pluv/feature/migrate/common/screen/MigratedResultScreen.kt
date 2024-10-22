package com.cmc15th.pluv.feature.migrate.common.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.core.designsystem.component.PLUVButton
import com.cmc15th.pluv.core.designsystem.component.PlaylistCard
import com.cmc15th.pluv.core.designsystem.component.SourceToDestinationText
import com.cmc15th.pluv.core.designsystem.component.TopAppBar
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray100
import com.cmc15th.pluv.core.designsystem.theme.Gray200
import com.cmc15th.pluv.core.designsystem.theme.Gray300
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.core.designsystem.theme.Title3
import com.cmc15th.pluv.core.designsystem.theme.Title4
import com.cmc15th.pluv.core.designsystem.theme.Title5
import com.cmc15th.pluv.core.ui.component.MusicItemWithIndexed
import com.cmc15th.pluv.core.ui.component.TransferredMusicTabRow
import com.cmc15th.pluv.feature.migrate.viewmodel.DirectMigrationUiEvent
import com.cmc15th.pluv.feature.migrate.viewmodel.DirectMigrationViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MigratedResultScreen(
    modifier: Modifier = Modifier,
    showSnackBar: (String) -> Unit,
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var inputPlaylistNameState by remember {
        mutableStateOf("")
    }

    var isSheetVisible by remember {
        mutableStateOf(false)
    }

    if (isSheetVisible) {
        ModifyPlaylistBottomSheet(
            sheetState = sheetState,
            playlistName = inputPlaylistNameState,
            imageUrl = uiState.selectedPlaylist.thumbNailUrl,
            onPlaylistNameChanged = {
                if (it.length <= 10) {
                    inputPlaylistNameState = it
                }
            },
            onDismissRequest = {
                isSheetVisible = false
            }
        )
    }

    LaunchedEffect(Unit) {
        showSnackBar("플레이리스트를 옮겼어요")
        viewModel.setEvent(DirectMigrationUiEvent.OnMigrationSucceed)
    }

    val musicItems =
        if (selectedTabIndex == 0) uiState.migratedMusics
        else uiState.notMigratedMusics

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().weight(1f)
        ) {
            TopAppBar(
                description = "옮긴 플레이리스트",
                onBackClick = navigateToHome
            )

            LazyColumn {
                item {
                    Column {
                        MigratedPlaylistCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            imageUrl = uiState.migrationResult.imageUrl,
                            playlistName = uiState.migrationResult.title,
                            sourceAppName = uiState.migrationResult.source,
                            destinationAppName = uiState.migrationResult.destination,
                            transferredMusicCount = uiState.migrationResult.transferredSongCount,
                            totalMusicCount = uiState.migrationResult.totalSongCount
                        )
                        PLUVButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 105.dp, vertical = 20.dp)
                                .border(1.dp, Gray300, RoundedCornerShape(8.dp)),
                            containerColor = Gray200,
                            contentColor = Gray800,
                            content = {
                                Text(
                                    text = "정보 수정하기",
                                    style = Content1
                                )
                            },
                            onClick = {
                                isSheetVisible = true
                            },
                        )
                    }
                }
                stickyHeader {
                    TransferredMusicTabRow(
                        modifier = Modifier.fillMaxWidth(),
                        selectedTabIndex = selectedTabIndex,
                        onTabSelected = { index ->
                            selectedTabIndex = index
                        },
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                itemsIndexed(musicItems) { index, music ->
                    MusicItemWithIndexed(
                        index = index,
                        musicName = music.title,
                        artistName = music.artistName,
                        imageUrl = music.imageUrl,
                    )
                }
            }
        }

        PLUVButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .border(1.dp, Gray300, RoundedCornerShape(8.dp)),
            containerColor = Color.Black,
            contentColor = Color.White,
            content = {
                Text(
                    text = "확인",
                    style = Content1
                )
            },
            onClick = {
                navigateToHome()
            },
        )
//        Divider(
//            modifier = Modifier.fillMaxWidth(),
//            thickness = 1.dp,
//            color = Gray300
//        )
    }
}

@Composable
fun MigratedResultTopBar(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.CenterStart)
        )

        Text(
            text = "옮긴 플레이리스트",
            style = Title4,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun MigratedPlaylistCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    playlistName: String,
    sourceAppName: String,
    destinationAppName: String,
    transferredMusicCount: Int,
    totalMusicCount: Int
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            PlaylistCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(302.dp),
                imageUrl = imageUrl,
                shape = RoundedCornerShape(24.dp),
            )
            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text = playlistName,
                style = Title1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.size(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SourceToDestinationText(
                    modifier = Modifier
                        .background(Color(0xFFEFF7FE), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 8.dp),
                    //FIXME
                    sourceApp = sourceAppName,
                    destinationApp = destinationAppName
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = "$transferredMusicCount/${totalMusicCount}곡 완료",
                    style = Content2,
                    color = Color(0xFF9E22FF),
                    modifier = Modifier
                        .background(Color(0xFFFBF5FF), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 8.dp),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyPlaylistBottomSheet(
    sheetState: SheetState,
    imageUrl: String,
    playlistName: String,
    onPlaylistNameChanged: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        containerColor = Color.White,
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = "플레이리스트 정보 수정",
                style = Title3,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.Center)
            )

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                modifier = Modifier
                    .size(12.dp)
                    .padding(end = 25.dp)
                    .align(Alignment.CenterEnd)
            )
        }

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Gray300
        )

        InputPlaylistNameTextField(
            value = playlistName,
            onValueChange = {
                onPlaylistNameChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .border(1.dp, Gray300, RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.size(23.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray100),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlaylistCard(
                imageUrl = imageUrl,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(top = 39.dp)
                    .size(204.dp)
            )

            PLUVButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 47.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
                    .height(58.dp),
                enabled = playlistName.isNotEmpty(),
                containerColor = Color.Black,
                contentColor = Color.White,
                content = {
                    Text(
                        text = "완료",
                        style = Title5,
                    )
                },
                onClick = {}
            )
        }
    }
}

@Composable
fun InputPlaylistNameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 11.dp, bottom = 11.dp, start = 12.dp)
                .align(Alignment.CenterStart),
            value = value,
            onValueChange = onValueChange,
            textStyle = Content1,
        )

        Text(
            text = "${value.length}/10",
            style = Content2,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        )

    }
}

@Preview
@Composable
fun MigrationResultTopBarPreview() {
    MigratedResultTopBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 14.dp)
    )
}

@Preview
@Composable
fun MigratedPlaylistCardPreview() {
    MigratedPlaylistCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        imageUrl = "https://picsum.photos/250/250",
        playlistName = "플레이리스트 이름",
        sourceAppName = "소스 앱",
        destinationAppName = "대상 앱",
        transferredMusicCount = 10,
        totalMusicCount = 20
    )
}

@Preview
@Composable
fun TextFieldPreview() {
    InputPlaylistNameTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        value = "새 제목을 입력해주세요",
        onValueChange = { /*TODO*/ }
    )
}