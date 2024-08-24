package com.cmc15th.pluv.ui.home.migrate.common.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray800

@Composable
fun SourceToDestinationText(
    sourceApp: String,
    destinationApp: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = if (sourceApp.isEmpty()) "" else "$sourceApp > $destinationApp",
        style = Content2,
        color = Gray800
    )
}