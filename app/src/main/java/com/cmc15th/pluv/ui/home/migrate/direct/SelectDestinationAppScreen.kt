package com.cmc15th.pluv.ui.home.migrate.direct

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
import com.cmc15th.pluv.core.designsystem.theme.SelectedAppName
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.domain.model.PlayListApp
import com.cmc15th.pluv.ui.home.getAppNameRes
import com.cmc15th.pluv.ui.home.getSelectedIconRes
import com.cmc15th.pluv.ui.home.migrate.component.PreviousOrMigrateButton

@Composable
fun SelectDestinationAppScreen(
    modifier: Modifier = Modifier,
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToSelectSource: () -> Unit,
    navigateToDisplayMigrationPath: () -> Unit
) {
    val state = viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
        Spacer(modifier = Modifier.weight(1f))
        PreviousOrMigrateButton(
            modifier = Modifier.size(58.dp),
            onPreviousClick = { navigateToSelectSource() },
            onMigrateClick = { navigateToDisplayMigrationPath() }
        )
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
            .fillMaxSize()
            .padding(24.dp),
        navigateToSelectSource = {},
        navigateToDisplayMigrationPath = {}
    )
}


