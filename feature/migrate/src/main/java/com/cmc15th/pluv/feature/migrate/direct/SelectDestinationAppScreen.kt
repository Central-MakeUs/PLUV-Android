package com.cmc15th.pluv.feature.migrate.direct

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cmc15th.pluv.core.designsystem.component.ExitDialog
import com.cmc15th.pluv.core.designsystem.component.LoadingDialog
import com.cmc15th.pluv.core.designsystem.component.TopBarWithProgress
import com.cmc15th.pluv.core.designsystem.theme.PrimaryDefault
import com.cmc15th.pluv.core.designsystem.theme.SelectedAppName
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.core.model.PlayListApp
import com.cmc15th.pluv.core.model.PlayListAppType
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.feature.migrate.common.component.PreviousOrMigrateButton
import com.cmc15th.pluv.feature.migrate.common.getAppIconRes
import com.cmc15th.pluv.feature.migrate.common.getAppNameRes
import com.cmc15th.pluv.feature.migrate.common.getSelectedIconRes
import com.cmc15th.pluv.feature.migrate.viewmodel.DirectMigrationUiEvent
import com.cmc15th.pluv.feature.migrate.viewmodel.DirectMigrationViewModel

@Composable
fun SelectDestinationAppScreen(
    modifier: Modifier = Modifier,
    currentStep: Int = 0,
    totalStep: Int = 0,
    onCloseClick: () -> Unit = {},
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToSelectSource: () -> Unit,
    navigateToDisplayMigrationPath: () -> Unit,
) {

    val state = viewModel.uiState.collectAsState()

    if (state.value.exitDialogState) {
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

    if (state.value.isLoading) {
        LoadingDialog(
            icon = {
                Icon(
                    painterResource(id = R.drawable.musicicon),
                    contentDescription = "fetch playlist loading",
                    tint = PrimaryDefault,
                    modifier = Modifier.size(50.dp)
                )
            },
            description = "음악을\n불러오는 중이에요!",
            onDismissRequest = {}
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
                isNextButtonEnabled = false,
                onPreviousClick = { navigateToSelectSource() },
                onMigrateClick = {
                    navigateToDisplayMigrationPath()
                }
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
            LazyColumn {
                items(state.value.destinationApps.filter { it.playListAppType == PlayListAppType.SERVICE }) { appItem ->
                    SelectAppItem(
                        appLogo = appItem.getAppIconRes(),
                        appName = appItem.getAppNameRes(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .clickable {
                                viewModel.setEvent(
                                    DirectMigrationUiEvent.SelectDestinationApp(
                                        appItem
                                    )
                                )
                                navigateToDisplayMigrationPath()
                            }
                    )
                }
            }
//            SelectAppColumn(
//                descriptionRes = R.string.select_destination_app,
//                serviceApps = state.value.destinationApps.filter { it.playListAppType == PlayListAppType.SERVICE },
//                pluvApps = state.value.destinationApps.filterNot { it.playListAppType == PlayListAppType.SERVICE },
//                onClick = {
//                    viewModel.setEvent(DirectMigrationUiEvent.SelectDestinationApp(it))
//                    navigateToDisplayMigrationPath()
//                }
//            )
        }
    }
}

@Composable
fun SourceToDestinationDots() {
    val circleColor = Color(0xFFD9D9D9)
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


