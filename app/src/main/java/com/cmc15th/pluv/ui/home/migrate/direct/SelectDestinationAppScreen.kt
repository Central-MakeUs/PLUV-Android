package com.cmc15th.pluv.ui.home.migrate.direct

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cmc15th.pluv.R
import com.cmc15th.pluv.ui.home.migrate.direct.DirectMigrationUiState.PlayListApp

@Composable
fun SelectDestinationAppScreen(
    modifier: Modifier = Modifier,
    viewModel: DirectMigrationViewModel = hiltViewModel(),
    navigateToExecute: () -> Unit
) {
    val state = viewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
                viewModel.setSelectedDestinationApp(it)
                navigateToExecute()
            }
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
            painter = painterResource(id = playListApp.selectedIcon),
            contentDescription = "${playListApp.appName} selectedIcon",
            tint = Color.Unspecified,
            modifier = Modifier.size(88.dp)
        )
        Spacer(modifier = Modifier.size(7.dp))
        Text(
            text = stringResource(id = playListApp.appName),
            fontSize = 14.sp,
            color = colorResource(id = R.color.sub_title_gray_color)
        )
    }
//    Column(
//        modifier = modifier,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Box(
//            modifier = Modifier
//                .size(88.dp)
//                .background(Color.Blue, RoundedCornerShape(24.dp)),
//            contentAlignment = Alignment.Center
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(74.dp)
//                    .background(Color.Gray, RoundedCornerShape(19.dp)),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    painter = painterResource(id = playListApp.appIcon),
//                    contentDescription = "${playListApp.appName} icon",
//                    tint = Color.Unspecified, // Use original icon colors
//                    modifier = Modifier
//                        .size(60.dp)
//                )
//            }
//        }
//        Spacer(modifier = Modifier.size(6.dp))
//        Text(
//            text = stringResource(id = playListApp.appName),
//            fontSize = 14.sp
//        )
//    }
}


