package com.cmc15th.pluv.ui.home.migrate.direct

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cmc15th.pluv.R
import com.cmc15th.pluv.ui.home.getAppNameRes
import com.cmc15th.pluv.core.designsystem.component.LoadingDialog
import com.cmc15th.pluv.ui.home.migrate.component.FetchPlaylistLoadingIcon

@Composable
fun DisplayMigrationPathScreen(
    modifier: Modifier = Modifier,
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToSelectPlaylist: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                DirectMigrationUiEffect.onSuccess -> {
                    navigateToSelectPlaylist()
                }
                DirectMigrationUiEffect.onFailure -> {
                    //TODO 에러 스낵바 표시
                }
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
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
                modifier = Modifier.align(Alignment.Start)
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
            Spacer(modifier = Modifier.weight(1f))
            PreviousOrMigrateButton(
                modifier = Modifier.size(58.dp),
                isNextButtonEnabled = true,
                onPreviousClick = { navigateToSelectDestinationApp() },
                onMigrateClick = {
                    viewModel.setEvent(DirectMigrationUiEvent.ExecuteMigration)
                }
            )
        }
    }
}

@Composable
fun DestinationAppText(
    @StringRes title: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row {
            Text(
                text = stringResource(id = title),
                fontSize = 24.sp,
                color = colorResource(id = R.color.destination_app_title_color)
            )
            Text(
                text = "으로",
                fontSize = 24.sp,
                color = Color.Black
            )
        }
        Text(
            text = stringResource(id = R.string.ask_migrate_playlist),
            fontSize = 24.sp,
            color = Color.Black
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

