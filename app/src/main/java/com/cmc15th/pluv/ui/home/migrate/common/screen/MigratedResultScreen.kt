package com.cmc15th.pluv.ui.home.migrate.common.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.cmc15th.pluv.core.designsystem.theme.Gray300
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.core.designsystem.theme.Title4
import com.cmc15th.pluv.core.designsystem.theme.Title5
import com.cmc15th.pluv.core.designsystem.theme.Title6
import com.cmc15th.pluv.ui.home.migrate.common.component.SourceToDestinationText
import com.cmc15th.pluv.ui.home.migrate.direct.DirectMigrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MigratedResultScreen(
    modifier: Modifier = Modifier,
    viewModel: DirectMigrationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        MigratedResultTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 12.dp, vertical = 14.dp)
        )
        MigratedPlaylistCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            imageUrl = "https://picsum.photos/250/250",
            playlistName = "플레이리스트 이름",
            sourceAppName = "소스 앱",
            destinationAppName = "대상 앱",
        )

        PLUVButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .border(1.dp, Gray300, RoundedCornerShape(8.dp)),
            containerColor = Color.White,
            contentColor = Color.Black,
            content = {
                Text(
                    text = "정보 수정하기",
                    style = Title5
                )
            },
            onClick = {

            },
        )
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
                    sourceApp = sourceAppName,
                    destinationApp = destinationAppName
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = "7/10곡 완료",
                    style = Title6,
                    color = Color(0xFF9E22FF),
                    modifier = Modifier
                        .background(Color(0xFFFBF5FF), shape = RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 8.dp),
                )
            }
        }
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
    )
}
