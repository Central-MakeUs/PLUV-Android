package com.cmc15th.pluv.ui.home.migrate.direct

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc15th.pluv.core.data.repository.PlaylistRepository
import com.cmc15th.pluv.domain.model.LoginMoment
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

    private val _loginMoment: MutableStateFlow<LoginMoment> = MutableStateFlow(LoginMoment.Source)
    val loginMoment: StateFlow<LoginMoment> = _loginMoment.asStateFlow()

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

            is DirectMigrationUiEvent.OnLoginSourceSuccess -> {
                fetchPlaylists()
            }

            is DirectMigrationUiEvent.OnLoginDestinationSuccess -> {
                validateSelectedMusic()
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

            is DirectMigrationUiEvent.SelectSourceMusic -> {
                val isSelectedMusicContain =
                    _uiState.value.selectedSourceMusics.contains(event.selectedMusic)

                val selectedMusics = _uiState.value.selectedSourceMusics.toMutableList()

                when (isSelectedMusicContain) {
                    true -> selectedMusics.remove(event.selectedMusic)
                    false -> selectedMusics.add(event.selectedMusic)
                }

                _uiState.update {
                    it.copy(
                        selectedSourceMusics = selectedMusics
                    )
                }
            }

            is DirectMigrationUiEvent.SelectAllSourceMusic -> {
                when (event.selectAllFlag) {
                    true -> {
                        _uiState.update {
                            it.copy(selectedSourceMusics = emptyList())
                        }
                    }

                    false -> {
                        _uiState.update {
                            it.copy(selectedSourceMusics = _uiState.value.allSourceMusics)
                        }
                    }
                }
            }

            is DirectMigrationUiEvent.SelectAllValidateMusic -> {
                when (event.selectAllFlag) {
                    true -> {
                        _uiState.update {
                            it.copy(selectedValidateMusics = emptyList())
                        }
                    }
                    false -> {
                        _uiState.update {
                            it.copy(selectedValidateMusics = it.validateMusics)
                        }
                    }
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

    private fun fetchPlaylists() {
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
                    sendEffect(DirectMigrationUiEffect.OnFetchPlaylistSuccess)
                }
                result.onFailure { i, s ->
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    sendEffect(DirectMigrationUiEffect.OnFailure)
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
                            allSourceMusics = data,
                            selectedSourceMusics = data
                        )
                    }
                    sendEffect(DirectMigrationUiEffect.OnFetchMusicSuccess)
                }

                result.onFailure { code, error ->
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    sendEffect(DirectMigrationUiEffect.OnFailure)
                    Log.d(TAG, "fetchMusicByPlaylist: $code, $error")
                }
            }
        }
    }

    fun setSpotifyAccessToken(accessToken: String?) {
        playlistAccessToken.update { accessToken ?: "" }
    }

    fun setLoginMoment(moment: LoginMoment) {
        _loginMoment.update { moment }
    }

    companion object {
        private const val TAG = "DirectMigrationViewModel"
    }
}