package com.cmc15th.pluv.ui.home.migrate.direct

import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.core.model.SourceMusic
import com.cmc15th.pluv.core.model.ValidateMusic
import com.cmc15th.pluv.domain.model.PlayListApp
import com.cmc15th.pluv.domain.model.PlayListApp.Companion.getAllPlaylistApps

data class DirectMigrationUiState(
    val isLoading: Boolean = false,
    val sourceApps: List<PlayListApp> = getAllPlaylistApps(),
    val destinationApps: List<PlayListApp> = emptyList(),
    val selectedSourceApp: PlayListApp = PlayListApp.EMPTY,
    val selectedDestinationApp: PlayListApp = PlayListApp.EMPTY,
    val allPlaylists: List<Playlist> = emptyList(),
    val selectedPlaylist: String = "",
    val allSourceMusics: List<SourceMusic> = emptyList(),
    val selectedSourceMusics: List<SourceMusic> = emptyList(),
    val validateMusics: List<ValidateMusic> = emptyList(),
    val selectedValidateMusics: List<ValidateMusic> = emptyList(),
)
