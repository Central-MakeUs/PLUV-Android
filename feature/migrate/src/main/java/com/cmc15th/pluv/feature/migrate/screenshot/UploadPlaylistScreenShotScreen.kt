package com.cmc15th.pluv.feature.migrate.screenshot

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.cmc15th.pluv.core.designsystem.component.PLUVButton
import com.cmc15th.pluv.core.designsystem.component.TopBarWithProgress
import com.cmc15th.pluv.core.designsystem.theme.BlueLight
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray100
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.core.designsystem.theme.pretendardFamily
import com.cmc15th.pluv.core.model.PlayListApp
import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.feature.migrate.viewmodel.DirectMigrationUiEvent
import com.cmc15th.pluv.feature.migrate.viewmodel.DirectMigrationViewModel

@Composable
fun UploadPlaylistScreenShotScreen(
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    currentStep: Int = 0,
    totalStep: Int = 0,
    onCloseClick: () -> Unit = {},
    navigateToSelectDestinationApp: () -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showUploadHelpTextState by remember {
        mutableStateOf(true)
    }

    val pickMedias =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
            viewModel.setEvent(DirectMigrationUiEvent.OnAddScreenShot(uris))
        }

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
            PLUVButton(
                onClick = {
                    viewModel.setEvent(DirectMigrationUiEvent.SelectSourceApp(PlayListApp.ScreenShot))
                    viewModel.setEvent(
                        DirectMigrationUiEvent.SelectPlaylist(
                            Playlist(
                                name = "스크린샷에서 옮긴 플레이리스트"
                            )
                        )
                    )
                    navigateToSelectDestinationApp()
                },
                containerColor = Color.Black,
                contentColor = Color.White,
                enabled = uiState.screenshotUris.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, start = 24.dp, end = 24.dp, top = 10.dp)
                    .background(Color.White)
            ) {
                Text("업로드 완료", style = Content1)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.size(28.dp))
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "플레이리스트의\n스크린샷을 업로드해주세요.",
                    style = Title1
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = "곡 명, 가수 등 모든 정보가 포함되도록 해주세요!",
                    style = Content1
                )
            }
            Spacer(modifier = Modifier.size(28.dp))

            ScreenShotUploadArea(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Gray100),
                images = uiState.screenshotUris,
                onSelectImagesClick = {
                    pickMedias.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                isHelpTextShow = showUploadHelpTextState,
                onImageDeleteClick = { index ->
                    viewModel.setEvent(DirectMigrationUiEvent.OnDeleteScreenShot(index))
                }
            )
        }
    }
}


@Composable
fun ScreenShotUploadArea(
    modifier: Modifier = Modifier,
    isHelpTextShow: Boolean = true,
    images: List<Uri> = emptyList(),
    onSelectImagesClick: () -> Unit = {},
    onImageDeleteClick: (Int) -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .size(21.dp)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            item {
                ScreenShotUploadButton(
                    modifier = Modifier
                        .width(206.dp)
                        .fillMaxHeight()
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .clickable { onSelectImagesClick() }
                )
            }
            itemsIndexed(images) { index, uri ->
                Box(
                    modifier = Modifier
                        .width(206.dp)
                        .fillMaxHeight()
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                ) {
                    AsyncImage(
                        model = uri,
                        contentDescription = "uploaded image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.trash_icon),
                        contentDescription = "delete image",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp)
                            .clickable { onImageDeleteClick(index) },
                        tint = Color.Unspecified
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .size(76.dp)
        )
        if (isHelpTextShow) {
            ScreenShotUploadHelpText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(
                        color = BlueLight
                    ),
            )
        }

        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
fun ScreenShotUploadButton(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(color = Color(0xFFF8F1FD), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.uploadbutton),
                contentDescription = "upload screenshot",
                modifier = Modifier.size(14.dp),
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Text(text = "이미지 업로드", style = Content2)
    }
}

@Composable
fun ScreenShotUploadHelpText(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Tip!",
                fontFamily = pretendardFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = Color(0xFF2E81FF),
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = "플레이리스트의 음악의 앨범 커버, 곡명, 가수명이 포함되도록 해주세요!",
                style = Content2,
                lineHeight = 20.sp
            )
        }
    }
}

@Preview
@Composable
fun UploadPlaylistScreenShotScreenPreview() {
    UploadPlaylistScreenShotScreen()
}