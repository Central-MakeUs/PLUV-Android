package com.cmc15th.pluv.ui.home.migrate.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.cmc15th.pluv.core.designsystem.theme.MigrateAppName

@Composable
fun SourceToDestinationText(
    sourceApp: String,
    destinationApp: String
) {
    Text(text = "$sourceApp > $destinationApp", style = MigrateAppName)
}