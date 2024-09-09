package com.cmc15th.pluv.feature.migrate.viewmodel

import android.net.Uri
import com.cmc15th.pluv.core.model.DestinationMusic
import com.cmc15th.pluv.core.model.HistoryDetail
import com.cmc15th.pluv.core.model.MigrationProcess
import com.cmc15th.pluv.core.model.PlayListApp
import com.cmc15th.pluv.core.model.PlayListApp.Companion.getAllPlaylistApps
import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.core.model.SourceMusic
import com.cmc15th.pluv.core.model.ValidateMusic

data class DirectMigrationUiState(
    val isLoading: Boolean = false,
    val exitDialogState: Boolean = false,
    val screenshotUris: List<Uri> = emptyList(),
    val sourceApps: List<PlayListApp> = getAllPlaylistApps(),
    val destinationApps: List<PlayListApp> = emptyList(),
    val selectedSourceApp: PlayListApp = PlayListApp.EMPTY,
    val selectedDestinationApp: PlayListApp = PlayListApp.EMPTY,
    val allPlaylists: List<Playlist> = emptyList(),
    val selectedPlaylist: Playlist = Playlist(),
    val allSourceMusics: List<SourceMusic> = emptyList(),
    val selectedSourceMusics: List<SourceMusic> = emptyList(),
    val similarMusics: List<ValidateMusic> = emptyList(),
    val selectedSimilarMusicsId: List<String> = emptyList(), // Index, Music Ids
    val notFoundMusics: List<DestinationMusic> = emptyList(),
    val notTransferMusics: List<DestinationMusic> = emptyList(),
    val migrationProcess: MigrationProcess = MigrationProcess(),
    val migrationResult: HistoryDetail = HistoryDetail(),
    val migratedMusics: List<SourceMusic> = emptyList(),
    val notMigratedMusics: List<SourceMusic> = emptyList(),
)
