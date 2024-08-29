package com.cmc15th.pluv.ui.home.migrate.direct

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.component.ExitDialog
import com.cmc15th.pluv.core.designsystem.component.TopBarWithProgress
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.SemiTitle1
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.domain.model.PlayListApp
import com.cmc15th.pluv.domain.model.PlayListAppType
import com.cmc15th.pluv.ui.home.getAppIconRes
import com.cmc15th.pluv.ui.home.getAppNameRes
import com.cmc15th.pluv.ui.home.migrate.common.component.PreviousOrMigrateButton

@Composable
fun SelectSourceAppScreen(
    modifier: Modifier = Modifier,
    currentStep: Int,
    totalStep: Int,
    onCloseClick: () -> Unit = {},
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToSelectDestinationApp: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

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
                isPreviousButtonEnabled = false,
                onPreviousClick = {},
                onMigrateClick = {}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SelectAppColumn(
                descriptionRes = R.string.select_source_app,
                serviceApps = uiState.value.sourceApps.filter { it.playListAppType == PlayListAppType.SERVICE },
                pluvApps = uiState.value.sourceApps.filterNot { it.playListAppType == PlayListAppType.SERVICE || it.playListAppType == PlayListAppType.SCREEN_SHOT },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onClick = {
                    viewModel.setEvent(DirectMigrationUiEvent.SelectSourceApp(it))
                    navigateToSelectDestinationApp()
                }
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun SelectAppColumn(
    descriptionRes: Int,
    serviceApps: List<PlayListApp>,
    pluvApps: List<PlayListApp>,
    modifier: Modifier = Modifier,
    onClick: (PlayListApp) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(
                text = stringResource(id = descriptionRes),
                style = Title1,
                modifier = Modifier.padding(24.dp)
            )
        }
        items(serviceApps.filter { it.playListAppType == PlayListAppType.SERVICE }) { appItem ->
            SelectAppItem(
                appLogo = appItem.getAppIconRes(),
                appName = appItem.getAppNameRes(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .clickable { onClick(appItem) }
            )
        }
        item {
            Column {
                Spacer(modifier = Modifier.size(30.dp))
                Text(
                    text = "플럽에서 불러오기",
                    style = Content1,
                    modifier = Modifier.padding(start = 24.dp, bottom = 10.dp)
                )
            }
        }
        items(pluvApps) { appItem ->
            SelectAppItem(
                appLogo = appItem.getAppIconRes(),
                appName = appItem.getAppNameRes(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .clickable { onClick(appItem) }
            )
        }
    }
}

@Composable
fun SelectFeedHistoryColumn() {

}

@Composable
fun SelectAppItem(
    appLogo: Int,
    appName: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = appLogo),
            contentDescription = "$appName icon",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(60.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(id = appName),
            style = SemiTitle1,
        )
    }
}

//@Preview
//@Composable
//fun SelectSourceAppScreenPreview() {
//    SelectSourceAppScreen(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(24.dp),
//        viewModel = DirectMigrationViewModel(),
//        navigateToSelectDestinationApp = {}
//    )
//}



