package com.cmc15th.pluv.ui.home.migrate.direct

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DirectMigrationViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<DirectMigrationUiState> =
        MutableStateFlow(DirectMigrationUiState())
    val uiState: StateFlow<DirectMigrationUiState> = _uiState.asStateFlow()

    fun setSelectedSourceApp(playListApp: DirectMigrationUiState.PlayListApp) {
        _uiState.update {
            it.copy(
                selectedSourceApp = playListApp,
                destinationApps = DirectMigrationUiState.getAllPlaylistApps().filter { apps ->
                    apps != playListApp
                }
            )
        }
    }

    fun setSelectedDestinationApp(playListApp: DirectMigrationUiState.PlayListApp) {
        _uiState.update {
            it.copy(selectedDestinationApp = playListApp)
        }
    }
}