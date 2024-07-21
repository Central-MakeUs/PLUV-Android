package com.cmc15th.pluv.ui.home.migrate.direct

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc15th.pluv.core.model.Music
import com.cmc15th.pluv.domain.model.PlayListApp
import com.cmc15th.pluv.domain.model.PlayListApp.Companion.getAllPlaylistApps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
class DirectMigrationViewModel @Inject constructor() : ViewModel() {

    private val _uiState: MutableStateFlow<DirectMigrationUiState> =
        MutableStateFlow(DirectMigrationUiState())
    val uiState: StateFlow<DirectMigrationUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<DirectMigrationUiEvent> = MutableSharedFlow()

    private val _uiEffect: Channel<DirectMigrationUiEffect> = Channel()
    val uiEffect: Flow<DirectMigrationUiEffect> = _uiEffect.receiveAsFlow()

    val selectedMusics = mutableStateOf(listOf<Long>())

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
                fetchPlaylists()
            }

            is DirectMigrationUiEvent.SelectPlaylist -> {
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
                    false -> selectedMusics.value = uiState.value.allMusics.map { it.id }
                }
            }
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

    private fun fetchPlaylists() {
        // Fetch playlists
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            delay(2000L)
            _uiState.update {
                it.copy(isLoading = false)
            }
            _uiEffect.send(DirectMigrationUiEffect.onSuccess)
            // Fetch playlists
            // onSuccess -> dialog 종료 후 화면 이동
            // onError -> dialog 종료 및 스낵바 표출
        }
    }

    private fun fetchMusicByPlaylist() {
        val musics = mutableListOf<Music>()
        for (i in 0 until 10) {
            musics.add(
                Music(
                    i.toLong(),
                    thumbNailUrl = "https://picsum.photos/140",
                    title = "Melody",
                    artist = "김동률"
                )
            )
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            delay(500L)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    allMusics = musics
                )
            }
            selectedMusics.value = musics.map { it.id }
            _uiEffect.send(DirectMigrationUiEffect.onSuccess)
            // Fetch music
            // onSuccess -> dialog 종료 후 화면 이동
            // onError -> dialog 종료 및 스낵바 표출
        }
    }

    fun setSpotifyAccessToken(accessToken: String?) {

    }
}