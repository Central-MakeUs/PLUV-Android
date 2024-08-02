package com.cmc15th.pluv.ui.home.migrate.direct

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc15th.pluv.core.data.repository.PlaylistRepository
import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.domain.model.PlayListApp
import com.cmc15th.pluv.domain.model.PlayListApp.Companion.getAllPlaylistApps
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
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

    private val playlistAccessToken = MutableStateFlow("")
    private val youtubeMusicAuthCode = MutableStateFlow("")

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

            is DirectMigrationUiEvent.SpotifyLogin -> {
                spotifyLogin(event.task)
            }

            is DirectMigrationUiEvent.GoogleLogin -> {
                googleLogin(event.task)
            }

            is DirectMigrationUiEvent.OnSourceLoginSuccess -> {
                fetchPlaylists(_uiState.value.selectedSourceApp)
            }

            is DirectMigrationUiEvent.ExecuteMigration -> {
                //TODO Source App Login
            }

            is DirectMigrationUiEvent.OnDestinationLoginSuccess -> {
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

            is DirectMigrationUiEvent.SelectSimilarMusic -> {
                val isSelectedMusicContain =
                    _uiState.value.selectedSimilarMusics.contains(event.selectedMusic)

                val selectedMusics = _uiState.value.selectedSimilarMusics.toMutableList()

                when (isSelectedMusicContain) {
                    true -> selectedMusics.remove(event.selectedMusic)
                    false -> selectedMusics.add(event.selectedMusic)
                }

                _uiState.update {
                    it.copy(
                        selectedSimilarMusics = selectedMusics
                    )
                }
            }

            is DirectMigrationUiEvent.SelectAllValidateMusic -> {
                when (event.selectAllFlag) {
                    true -> {
                        _uiState.update {
                            it.copy(selectedSimilarMusics = emptyList())
                        }
                    }

                    false -> {
                        _uiState.update {
                            it.copy(selectedSimilarMusics = it.similarMusics)
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
    private fun spotifyLogin(task: AuthorizationResponse) {
        when (task.type) {
            AuthorizationResponse.Type.TOKEN -> {
                playlistAccessToken.update { task.accessToken ?: "" }
                sendEffect(DirectMigrationUiEffect.OnLoginSuccess)
            }

            else -> {
                val error = task.error
                sendEffect(DirectMigrationUiEffect.OnFailure)
            }
        }
    }

    private fun googleLogin(task: Task<GoogleSignInAccount>?) {
        if (task == null) {
            sendEffect(DirectMigrationUiEffect.OnFailure)
            return
        }

        try {
            val account = task.getResult(ApiException::class.java)
            Log.d(TAG, "googleLogin: ${account.serverAuthCode}")
            youtubeMusicAuthCode.update { account.serverAuthCode ?: "" }
            sendEffect(DirectMigrationUiEffect.OnLoginSuccess)

        } catch (e: ApiException) {
            sendEffect(DirectMigrationUiEffect.OnFailure)
        }
    }

        viewModelScope.launch {
            Log.d(TAG, "fetchPlaylists: ${playlistAccessToken.value} ")
            _uiState.update {
                it.copy(isLoading = true)
            }
            playlistRepository.fetchPlaylists(
                playlistAccessToken.value
            ).collect { result ->
                Log.d(TAG, "fetchPlaylists: $result")
                result.onSuccess { data ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            allPlaylists = data,
                        )
                    }
                    sendEffect(DirectMigrationUiEffect.OnFetchPlaylistSuccess)
                }

                result.onFailure { code, error ->
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    sendEffect(DirectMigrationUiEffect.OnFailure)
                    Log.d(TAG, "fetchPlaylists: $code, $error")
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

    /**
     * 유저가 선택한 옮길 음악 Destination App에서 존재하는지 검증하여 일치하지 않지만 유사한 음악 + 존재하지 않는 음악 응답값으로 받는 함수
     */
    private fun validateSelectedMusic() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            playlistRepository.validateMusic(
                //FIXME playlistAppName = _uiState.value.selectedDestinationApp,
                playlistAppName = PlayListApp.spotify,
                accessToken = playlistAccessToken.value,
                musics = _uiState.value.selectedSourceMusics
            ).collect { result ->
                Log.d(TAG, "validateSelectedMusic: $result")
                result.onSuccess { data ->

                    val updatedData = data.map { validateMusic ->
                        val matchingSourceMusic =
                            _uiState.value.selectedSourceMusics.find { sourceMusic ->
                                sourceMusic.title == validateMusic.sourceMusic.title
                            }

                        // `validateMusic`의 `sourceMusic`을 업데이트된 `matchingSourceMusic`으로 교체
                        if (matchingSourceMusic != null) {
                            validateMusic.copy(sourceMusic = matchingSourceMusic)
                        } else {
                            validateMusic
                        }
                    }
                    // isEqual = true, isFound = true 인 경우는 유저가 선택한 음악이 Destination App에 존재하는 경우 (검증 필요X)
                    val similarMusics = updatedData.filter {
                        !it.isEqual && it.isFound
                    }

                    val notFoundMusics = updatedData.filter {
                        !it.isEqual && !it.isFound
                    }.map { it.destinationMusic }

                    val needValidate = similarMusics.isNotEmpty() || notFoundMusics.isNotEmpty()

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            similarMusics = similarMusics,
                            selectedSimilarMusics = similarMusics,
                            notFoundMusics = notFoundMusics
                        )
                    }
                    Log.d(
                        TAG,
                        "validateSelectedMusic:  $similarMusics, $notFoundMusics  $needValidate"
                    )
                    sendEffect(DirectMigrationUiEffect.OnValidateMusic(needValidate = needValidate))
                }

                result.onFailure { code, error ->
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    sendEffect(DirectMigrationUiEffect.OnFailure)
                    Log.d(TAG, "validateSelectedMusic: $code, $error")
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