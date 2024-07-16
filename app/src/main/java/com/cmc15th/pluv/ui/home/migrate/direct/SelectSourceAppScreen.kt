package com.cmc15th.pluv.ui.home.migrate.direct

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cmc15th.pluv.R
import com.cmc15th.pluv.domain.model.PlayListApp
import com.cmc15th.pluv.ui.home.getAppIconRes
import com.cmc15th.pluv.ui.home.getAppNameRes

@Composable
fun SelectSourceAppScreen(
    modifier: Modifier = Modifier,
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToSelectDestinationApp: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.select_source_app),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.size(31.dp))
        SelectAppColumn(
            playListApps = uiState.value.sourceApps,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onClick = {
                viewModel.setEvent(DirectMigrationUiEvent.SelectSourceApp(it))
                navigateToSelectDestinationApp()
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        PreviousOrMigrateButton(
            modifier = Modifier.size(58.dp),
            isPreviousButtonEnabled = false,
            onPreviousClick = {},
            onMigrateClick = { /*TODO*/ }
        )
    }
}

@Composable
fun SelectAppColumn(
    playListApps: List<PlayListApp>,
    modifier: Modifier = Modifier,
    onClick: (PlayListApp) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(playListApps) { appItem ->
            SelectAppItem(
                appLogo = appItem.getAppIconRes(),
                appName = appItem.getAppNameRes(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp, vertical = 20.dp)
                    .clickable { onClick(appItem) }
            )
        }
    }
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
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}



