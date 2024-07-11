package com.cmc15th.pluv.ui.home.migrate.direct

import com.cmc15th.pluv.domain.model.PlayListApp
import com.cmc15th.pluv.domain.model.PlayListApp.Companion.getAllPlaylistApps

data class DirectMigrationUiState(
    val sourceApps: List<PlayListApp> = getAllPlaylistApps(),
    val destinationApps: List<PlayListApp> = emptyList(),
    val selectedSourceApp: PlayListApp = PlayListApp.EMPTY,
    val selectedDestinationApp: PlayListApp = PlayListApp.EMPTY,
)
