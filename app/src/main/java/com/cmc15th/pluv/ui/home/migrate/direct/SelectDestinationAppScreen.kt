package com.cmc15th.pluv.ui.home.migrate.direct

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.component.TopBarWithProgress
import com.cmc15th.pluv.core.designsystem.theme.SelectedAppName
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.domain.model.PlayListApp
import com.cmc15th.pluv.ui.home.getAppNameRes
import com.cmc15th.pluv.ui.home.getSelectedIconRes
import com.cmc15th.pluv.ui.home.migrate.common.component.PreviousOrMigrateButton

@Composable
fun SelectDestinationAppScreen(
    modifier: Modifier = Modifier,
    currentStep: Int = 0,
    totalStep: Int = 0,
    onCloseClick: () -> Unit = {},
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToSelectSource: () -> Unit,
    navigateToDisplayMigrationPath: () -> Unit
) {

    val state = viewModel.uiState.collectAsState()

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
                onPreviousClick = { navigateToSelectSource() },
                onMigrateClick = { navigateToDisplayMigrationPath() }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "어디로\n플레이리스트를 옮길까요?",
                style = Title1,
                modifier = Modifier.align(Alignment.Start),
            )
            Spacer(modifier = Modifier.size(32.dp))
            SelectedAppItem(
                playListApp = state.value.selectedSourceApp,
            )
            Spacer(modifier = Modifier.size(23.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SourceToDestinationDots()
                SourceToDestinationDots()
                SourceToDestinationDots()
            }
            Spacer(modifier = Modifier.size(13.dp))
            SelectAppColumn(
                playListApps = state.value.destinationApps,
                onClick = {
                    viewModel.setEvent(DirectMigrationUiEvent.SelectDestinationApp(it))
                    navigateToDisplayMigrationPath()
                }
            )
        }
    }
}

@Composable
fun SourceToDestinationDots() {
    val circleColor = colorResource(id = R.color.circle_color)
    Canvas(modifier = Modifier.size(6.dp)) {
        drawCircle(color = circleColor)
    }
}

@Composable
fun SelectedAppItem(
    playListApp: PlayListApp,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = playListApp.getSelectedIconRes()),
            contentDescription = "${playListApp.name} selectedIcon",
            tint = Color.Unspecified,
            modifier = Modifier.size(88.dp)
        )
        Spacer(modifier = Modifier.size(7.dp))
        Text(
            text = stringResource(id = playListApp.getAppNameRes()),
            style = SelectedAppName
        )
    }
}

@Preview
@Composable
fun SelectDestinationAppScreenPreview() {
    SelectDestinationAppScreen(
        modifier = Modifier
            .fillMaxSize(),
        navigateToSelectSource = {},
        navigateToDisplayMigrationPath = {}
    )
}


