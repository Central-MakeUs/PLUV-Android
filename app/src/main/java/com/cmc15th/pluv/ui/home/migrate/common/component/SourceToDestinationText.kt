package com.cmc15th.pluv.ui.home.migrate.common.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cmc15th.pluv.core.designsystem.theme.MigrateAppName

@Composable
fun SourceToDestinationText(
    sourceApp: String,
    destinationApp: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = "$sourceApp > $destinationApp",
        style = MigrateAppName
    )
}