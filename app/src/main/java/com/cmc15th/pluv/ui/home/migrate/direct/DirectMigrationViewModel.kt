package com.cmc15th.pluv.ui.home.migrate.direct

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc15th.pluv.core.data.repository.PlaylistRepository
import com.cmc15th.pluv.domain.model.PlayListApp
import com.cmc15th.pluv.domain.model.PlayListApp.Companion.getAllPlaylistApps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DirectMigrationViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DirectMigrationUiState> =
        MutableStateFlow(DirectMigrationUiState())
    val uiState: StateFlow<DirectMigrationUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<DirectMigrationUiEvent> = MutableSharedFlow()

    private val _uiEffect: Channel<DirectMigrationUiEffect> = Channel()
    val uiEffect: Flow<DirectMigrationUiEffect> = _uiEffect.receiveAsFlow()

    val selectedMusics = mutableStateOf(listOf<String>())

    private val playlistAccessToken = MutableStateFlow("")

    init {
        subscribeEvents()
    }

    fun setEvent(event: DirectMigrationUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            _uiEvent.collect {
                handleEvent(it)
            }
        }
    }

    private fun handleEvent(event: DirectMigrationUiEvent) {
        when (event) {
            is DirectMigrationUiEvent.SelectSourceApp -> {
                setSelectedSourceApp(event.selectedApp)
            }

            is DirectMigrationUiEvent.SelectDestinationApp -> {
                setSelectedDestinationApp(event.selectedApp)
            }

            is DirectMigrationUiEvent.ExecuteMigration -> {
                //TODO Source App Login
            }

            is DirectMigrationUiEvent.SelectPlaylist -> {
                _uiState.update {
                    it.copy(
                        selectedPlaylist = event.selectedPlaylistId
                    )
                }
            }

            is DirectMigrationUiEvent.FetchMusicsByPlaylist -> {
                fetchMusicByPlaylist()
            }

            is DirectMigrationUiEvent.SelectMusic -> {
                selectedMusics.value = selectedMusics.value.toMutableList().apply {
                    if (contains(event.selectedMusicId)) {
                        remove(event.selectedMusicId)
                    } else {
                        add(event.selectedMusicId)
                    }
                }
            }

            is DirectMigrationUiEvent.SelectAllMusic -> {
                when (event.selectAllFlag) {
                    true -> selectedMusics.value = emptyList()
                    false -> selectedMusics.value = uiState.value.allMusics.map { it.isrcCode }
                }
            }
        }
    }

    private fun sendEffect(effect: DirectMigrationUiEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

    private fun setSelectedSourceApp(playListApp: PlayListApp) {
        _uiState.update {
            it.copy(
                selectedSourceApp = playListApp,
                destinationApps = getAllPlaylistApps().filter { apps ->
                    apps != playListApp
                }
            )
        }
    }

    private fun setSelectedDestinationApp(playListApp: PlayListApp) {
        _uiState.update {
            it.copy(selectedDestinationApp = playListApp)
        }
    }

    fun fetchPlaylists() {
        // Fetch playlists
        viewModelScope.launch {
            Log.d(TAG, "fetchPlaylists: ${playlistAccessToken.value} ")
            _uiState.update {
                it.copy(isLoading = true)
            }
            playlistRepository.fetchPlaylists(
                _uiState.value.selectedSourceApp,
                playlistAccessToken.value
            ).collect { result ->
                Log.d(TAG, "fetchPlaylists: $result")
                result.onSuccess { data ->
                    _uiState.update {
                        it.copy(
                            allPlaylists = data,
                            isLoading = false
                        )
                    }
                }
                result.onFailure { i, s ->
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }

    private fun fetchMusicByPlaylist() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            playlistRepository.fetchMusics(
                accessToken = playlistAccessToken.value,
                playlistAppName = _uiState.value.selectedSourceApp,
                playlistId = _uiState.value.selectedPlaylist
            ).collect { result ->
                Log.d(TAG, "fetchMusicByPlaylist: $result")
                result.onSuccess { data ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            allMusics = data
                        )
                    }
                    sendEffect(DirectMigrationUiEffect.onSuccess)
                }
                result.onFailure { code, error ->
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    sendEffect(DirectMigrationUiEffect.onFailure)
                    Log.d(TAG, "fetchMusicByPlaylist: $code, $error")
                }
            }
        }
    }

    fun setSpotifyAccessToken(accessToken: String?) {
        playlistAccessToken.update { accessToken ?: "" }
    }

    companion object {
        private const val TAG = "DirectMigrationViewModel"
    }
}