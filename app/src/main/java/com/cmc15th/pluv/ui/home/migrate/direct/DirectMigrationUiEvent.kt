package com.cmc15th.pluv.ui.home.migrate.direct

import com.cmc15th.pluv.domain.model.PlayListApp

sealed class DirectMigrationUiEvent {
    class SelectSourceApp(val selectedApp: PlayListApp) : DirectMigrationUiEvent()
    class SelectDestinationApp(val selectedApp: PlayListApp) : DirectMigrationUiEvent()
    data object ExecuteMigration : DirectMigrationUiEvent()
    data object SelectPlaylist : DirectMigrationUiEvent()
    class SelectMusic(val selectedMusicId: Long) : DirectMigrationUiEvent()
}