package com.cmc15th.pluv.ui.home.migrate.common.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.component.PLUVButton
import com.cmc15th.pluv.core.designsystem.component.PlaylistCard
import com.cmc15th.pluv.core.designsystem.theme.Content0
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray300
import com.cmc15th.pluv.core.designsystem.theme.Gray600
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.PrimaryBg
import com.cmc15th.pluv.core.designsystem.theme.PrimaryDefault
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.core.designsystem.theme.Title4
import com.cmc15th.pluv.core.designsystem.theme.Title5
import com.cmc15th.pluv.core.designsystem.theme.Violet200
import com.cmc15th.pluv.ui.home.getAppIconRes
import com.cmc15th.pluv.ui.home.migrate.direct.DirectMigrationUiEffect
import com.cmc15th.pluv.ui.home.migrate.direct.DirectMigrationUiEvent
import com.cmc15th.pluv.ui.home.migrate.direct.DirectMigrationViewModel

private const val Progress_Width = 8

@Composable
fun MigrationProcessScreen(
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    showSnackBar: (String) -> Unit = {},
    navigateToHome: () -> Unit = {},
    onStopMigrationClicked: () -> Unit = {},
    navigateToMigrationResult: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setEvent(DirectMigrationUiEvent.ExecuteMigration)

        viewModel.uiEffect.collect { effect ->
            when (effect) {
                DirectMigrationUiEffect.OnMigrationSuccess -> {
                    navigateToMigrationResult()
                }
                DirectMigrationUiEffect.OnFailure -> {
                    showSnackBar("작업 중 오류가 발생했습니다.")
                    navigateToHome()
                }
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "플레이리스트 옮기기",
                        style = Content0,
                        color = Color(0xFF090A0A),
                        modifier = Modifier
                            .padding(vertical = 14.dp)
                            .align(Alignment.Center)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.close_button),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .size(14.dp)
                            .align(Alignment.CenterEnd)
                    )
                }
            }

            MigrationProcessCircleIndicator(
                modifier = Modifier.align(Alignment.Center),
                playlistImageUrl = uiState.selectedPlaylist.thumbNailUrl,
                destinationAppImageRes = uiState.selectedDestinationApp.getAppIconRes(),
                currentMigrationCount = uiState.migrationProcess.transferredMusicCount,
                totalMigrationCount = uiState.migrationProcess.willTransferMusicCount
            )

            MigratePlaylistInfo(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                playlistName = uiState.selectedPlaylist.name,
                sourceAppName = uiState.selectedSourceApp.appName,
                destinationAppName = uiState.selectedDestinationApp.appName,
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = Gray300
        )
        PLUVButton(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(top = 10.dp, bottom = 34.dp, start = 24.dp, end = 24.dp),
            onClick = { /*TODO*/ },
            containerColor = Color.Black,
            contentColor = Color.White,
            contentPadding = PaddingValues(19.dp),
            content = {
                Text(
                    text = "작업 중단하기",
                    style = Title5
                )
            }
        )
    }
}

@Composable
fun MigrationProcessCircleIndicator(
    modifier: Modifier = Modifier,
    playlistImageUrl: String,
    destinationAppImageRes: Int,
    currentMigrationCount: Int,
    totalMigrationCount: Int
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(308.dp)
            .padding(horizontal = 41.dp)
            .drawBehind {
                val circleRadius = (size.minDimension / 2) - (Progress_Width.dp.toPx() / 2)
                drawCircle(
                    color = Color.White,
                    radius = circleRadius,
                    center = center
                )
            }
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize(),
            trackColor = PrimaryBg,
            strokeWidth = Progress_Width.dp
        )
        PlaylistToDestination(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            playlistImageUrl = playlistImageUrl,
            destinationAppImageRes = destinationAppImageRes,
            currentMigrationCount = currentMigrationCount,
            totalMigrationCount = totalMigrationCount
        )
    }
}

@Composable
fun PlaylistToDestination(
    modifier: Modifier = Modifier,
    playlistImageUrl: String,
    destinationAppImageRes: Int,
    currentMigrationCount: Int,
    totalMigrationCount: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PlaylistCard(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .size(52.dp),
            imageUrl = playlistImageUrl,
            shape = RoundedCornerShape(10.dp)
        )

        CircleIndicator(
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(9.dp))

        CircleIndicator(
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(9.dp))

        CircleIndicator(
            modifier = Modifier
        )
        Image(
            painter = painterResource(id = destinationAppImageRes),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 12.dp, bottom = 14.dp)
                .size(97.dp)
        )
        Row {
            Text(
                text = "$currentMigrationCount ",
                style = Title1,
                color = PrimaryDefault
            )

            Text(
                text = "/ ${totalMigrationCount}곡",
                style = Title1,
                color = Gray600
            )
        }
    }
}

@Composable
fun MigratePlaylistInfo(
    modifier: Modifier = Modifier,
    playlistName: String,
    sourceAppName: String,
    destinationAppName: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menu_04),
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = playlistName,
                style = Title4,
                color = Gray800,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sourceAppName,
                style = Content2,
                color = Color(0xFF2E81FF),
            )
            Icon(
                painter = painterResource(id = R.drawable.rightarrow),
                contentDescription = null,
                modifier = Modifier
                    .size(18.dp),
                tint = Color(0xFF2E81FF)
            )
            Text(
                text = destinationAppName,
                style = Content2,
                color = Color(0xFF2E81FF),
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun CircleIndicator(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(6.dp)) {
        drawCircle(
            color = Violet200,
            radius = 8f
        )
    }
}


@Preview
@Composable
fun MigrationProcessScreenPreview() {
    MigrationProcessScreen()
}