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
    class SelectSourceMusic(val selectedMusicId: String) : DirectMigrationUiEvent()
    class SelectAllSourceMusic(val selectAllFlag: Boolean) : DirectMigrationUiEvent()
}