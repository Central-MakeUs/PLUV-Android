package com.cmc15th.pluv.ui.home.migrate.direct

import com.cmc15th.pluv.domain.model.PlayListApp

sealed class DirectMigrationUiEvent {
    class SelectSourceApp(val selectedApp: PlayListApp) : DirectMigrationUiEvent()
    class SelectDestinationApp(val selectedApp: PlayListApp) : DirectMigrationUiEvent()
    data object ExecuteMigration : DirectMigrationUiEvent()
    data object OnLoginSourceSuccess: DirectMigrationUiEvent()
    data object OnLoginDestinationSuccess: DirectMigrationUiEvent()
    class SelectPlaylist(val selectedPlaylistId: String) : DirectMigrationUiEvent()
    data object FetchMusicsByPlaylist : DirectMigrationUiEvent()
    class SelectMusic(val selectedMusicId: String) : DirectMigrationUiEvent()
    class SelectAllMusic(val selectAllFlag: Boolean) : DirectMigrationUiEvent()
}