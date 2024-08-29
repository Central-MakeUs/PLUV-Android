package com.cmc15th.pluv.ui.home.migrate.direct

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc15th.pluv.core.data.mapper.toPlaylist
import com.cmc15th.pluv.core.data.repository.FeedRepository
import com.cmc15th.pluv.core.data.repository.LoginRepository
import com.cmc15th.pluv.core.data.repository.MemberRepository
import com.cmc15th.pluv.core.data.repository.PlaylistRepository
import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.DestinationMusic
import com.cmc15th.pluv.domain.model.PlayListApp
import com.cmc15th.pluv.domain.model.PlayListApp.Companion.getAllPlaylistApps
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
    private val playlistRepository: PlaylistRepository,
    private val loginRepository: LoginRepository,
    private val feedRepository: FeedRepository,
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DirectMigrationUiState> =
        MutableStateFlow(DirectMigrationUiState())
    val uiState: StateFlow<DirectMigrationUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<DirectMigrationUiEvent> = MutableSharedFlow()

    private val _uiEffect: Channel<DirectMigrationUiEffect> = Channel()
    val uiEffect: Flow<DirectMigrationUiEffect> = _uiEffect.receiveAsFlow()

    // 이전할 음악 목록들
    private val destinationMusicsIds: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

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
            is DirectMigrationUiEvent.OnAddScreenShot -> {
                val currentUris = _uiState.value.screenshotUris.toMutableList()
                currentUris.addAll(event.uris)
                _uiState.update {
                    it.copy(screenshotUris = currentUris)
                }
            }

            is DirectMigrationUiEvent.SelectSourceApp -> {
                setSelectedSourceApp(event.selectedApp)
            }

            is DirectMigrationUiEvent.SelectDestinationApp -> {
                setSelectedDestinationApp(event.selectedApp)
            }

            is DirectMigrationUiEvent.FetchSavedFeed -> {
                fetchSavedFeed()
            }

            is DirectMigrationUiEvent.FetchHistory -> {
                fetchHistories()
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

            is DirectMigrationUiEvent.OnDestinationLoginSuccess -> {
                validateSelectedMusic()
            }

            is DirectMigrationUiEvent.SelectPlaylist -> {
                _uiState.update {
                    it.copy(
                        selectedPlaylist = event.playlist
                    )
                }
            }

            is DirectMigrationUiEvent.FetchMusicsByPlaylist -> {
                fetchMusicByPlaylist(_uiState.value.selectedSourceApp)
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
                val selectedIndex = event.index
                val selectedId = event.selectedMusicId
                val selectedSimilarMusicsId = _uiState.value.selectedSimilarMusicsId.toMutableList()

                if (selectedSimilarMusicsId[selectedIndex] == selectedId) {
                    selectedSimilarMusicsId[selectedIndex] = ""
                } else {
                    selectedSimilarMusicsId[selectedIndex] = selectedId
                }

                _uiState.update {
                    it.copy(selectedSimilarMusicsId = selectedSimilarMusicsId)
                }
            }

            is DirectMigrationUiEvent.ExecuteMigration -> {
                migratePlaylist()
            }

            is DirectMigrationUiEvent.OnMigrationSucceed -> {
                getMigrationResult()
            }

            is DirectMigrationUiEvent.ShowExitMigrationDialog -> {
                _uiState.update {
                    it.copy(exitDialogState = it.exitDialogState.not())
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

    private fun spotifyLogin(task: AuthorizationResponse) {
        when (task.type) {
            AuthorizationResponse.Type.TOKEN -> {
                Log.d(TAG, "spotifyLogin: ${task.accessToken}")
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

        setLoadingState(true)

        try {
            val account = task.getResult(ApiException::class.java)
            Log.d(TAG, "googleLogin: ${account.serverAuthCode}")
            getGoogleAccessToken(account.serverAuthCode ?: "")

        } catch (e: ApiException) {
            setLoadingState(false)
            sendEffect(DirectMigrationUiEffect.OnFailure)
        }
    }

    private fun getGoogleAccessToken(authCode: String) {
        if (authCode.isEmpty()) {
            sendEffect(DirectMigrationUiEffect.OnFailure)
            return
        }

        viewModelScope.launch {
            loginRepository.getGoogleAccessToken(authCode).collect { result ->
                result.onSuccess { token ->
                    Log.d(TAG, "getGoogleAccessToken: ${token.accessToken}")
                    playlistAccessToken.update { token.accessToken }
                    sendEffect(DirectMigrationUiEffect.OnLoginSuccess)
                }

                result.onFailure { code, error ->
                    setLoadingState(false)
                    sendEffect(DirectMigrationUiEffect.OnFailure)
                    Log.d(TAG, "getGoogleAccessToken: $code, $error")
                }
            }
        }
    }

    private fun fetchSavedFeed() {
        viewModelScope.launch {
            setLoadingState(true)
            feedRepository.getSavedFeeds().collect { result ->
                result.onSuccess { data ->
                    _uiState.update {
                        it.copy(allPlaylists = data.map { feed -> feed.toPlaylist() })
                    }
                    sendEffect(DirectMigrationUiEffect.OnFetchPlaylistSuccess)
                    setLoadingState(false)
                }
                result.onFailure { code, error ->
                    Log.d(TAG, "fetchSavedFeed: $code, $error")
                    setLoadingState(false)
                }
            }
        }
    }

    private fun fetchHistories() {
        viewModelScope.launch {
            setLoadingState(true)
            memberRepository.getHistories().collect { result ->
                result.onSuccess { data ->
                    _uiState.update {
                        it.copy(allPlaylists = data.map { history -> history.toPlaylist() })
                    }
                    sendEffect(DirectMigrationUiEffect.OnFetchPlaylistSuccess)
                    setLoadingState(false)
                }

                result.onFailure { code, error ->
                    setLoadingState(false)
                    sendEffect(DirectMigrationUiEffect.OnFailure)
                }
            }
        }
    }

    private fun fetchPlaylists(sourcePlaylistApp: PlayListApp) {
        viewModelScope.launch {
            when (sourcePlaylistApp) {
                PlayListApp.Spotify -> {
                    playlistRepository.fetchSpotifyPlaylists(
                        accessToken = playlistAccessToken.value
                    )
                }

                PlayListApp.YoutubeMusic -> {
                    playlistRepository.fetchYoutubeMusicPlaylists(
                        accessToken = playlistAccessToken.value
                    )
                }

                else -> {
                    //TODO 구현예정
                    flow<ApiResult.Failure> { ApiResult.Failure(-1, "구현중") }
                }
            }.collect { result ->
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

    private fun fetchMusicByPlaylist(sourcePlaylistApp: PlayListApp) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            Log.d(TAG, "fetchMusicByPlaylist: ${_uiState.value.selectedPlaylist}")
            when (sourcePlaylistApp) {
                PlayListApp.Spotify -> {
                    playlistRepository.fetchSpotifyMusics(
                        accessToken = playlistAccessToken.value,
                        playlistId = _uiState.value.selectedPlaylist.id,
                    )
                }

                PlayListApp.YoutubeMusic -> {
                    Log.d(TAG, "fetchMusicByPlaylist: ${playlistAccessToken.value}")
                    playlistRepository.fetchYoutubeMusics(
                        accessToken = playlistAccessToken.value,
                        playlistId = _uiState.value.selectedPlaylist.id
                    )
                }

                PlayListApp.History -> {
                    memberRepository.getTransferSucceedHistoryMusics(_uiState.value.selectedPlaylist.id.toInt())
                }

                PlayListApp.Feed -> {
                    feedRepository.getFeedMusics(_uiState.value.selectedPlaylist.id.toLong())
                }

                else -> {
                    //TODO 구현예정
                    flow<ApiResult.Failure> { ApiResult.Failure(-1, "구현중") }
                }
            }.collect { result ->
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
                playlistAppName = _uiState.value.selectedDestinationApp,
                accessToken = playlistAccessToken.value,
                musics = _uiState.value.selectedSourceMusics
            ).collect { result ->
                Log.d(TAG, "validateSelectedMusic: $result")
                result.onSuccess { data ->

                    // isEqual = true, isFound = true 인 경우는 유저가 선택한 음악이 Destination App에 존재하는 경우 (검증 필요X)
                    // 여기선 유효성 검사에 걸리는 음악은 제외되고 추가됨
                    destinationMusicsIds.update {
                        val validMusicIds = data
                            .filter { it.isFound && it.isEqual }
                            .flatMap { it.destinationMusic }
                            .map { it.id }

                        validMusicIds
                    }

                    // isEqual = true, isFound = true 인 경우는 유저가 선택한 음악이 Destination App에 존재하는 경우 (검증 필요X)
                    val similarMusics = data.filter {
                        !it.isEqual && it.isFound
                    }

                    val notFoundMusics = data.filter {
                        !it.isEqual && !it.isFound
                    }.flatMap {
                        it.destinationMusic
                    }

                    val needValidate = similarMusics.isNotEmpty() || notFoundMusics.isNotEmpty()

                    // 유사 음악의 첫번째 음악의 id를 선택된 유사 음악으로 설정, 없을 경우 빈 리스트 + 선택된 유사 음악 빈 문자열로 설정
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            similarMusics = similarMusics,
                            selectedSimilarMusicsId = similarMusics.map { musics ->
                                musics.destinationMusic.firstOrNull()?.id ?: ""
                            },
                            notFoundMusics = notFoundMusics,
                        )
                    }
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

    private fun migratePlaylist() {
        viewModelScope.launch {
            //FIXME 추후  수정예정
            var sourceName = _uiState.value.selectedSourceApp.sourceName
            if (sourceName == "feed" || sourceName == "history") {
                sourceName = _uiState.value.selectedDestinationApp.sourceName
            }
            val migrateTargetMusicIds =
                destinationMusicsIds.value + _uiState.value.selectedSimilarMusicsId

            val notTransferMusics = getNotTransferMusic()

            Log.d(TAG, "migratePlaylist: $migrateTargetMusicIds, $notTransferMusics")
            when (_uiState.value.selectedDestinationApp) {
                PlayListApp.Spotify -> {
                    playlistRepository.migrateToSpotify(
                        playlistName = _uiState.value.selectedPlaylist.name,
                        accessToken = playlistAccessToken.value,
                        musicIds = migrateTargetMusicIds,
                        thumbnailUrl = _uiState.value.selectedPlaylist.thumbNailUrl,
                        source = sourceName,
                        transferFailMusics = notTransferMusics
                    )
                }

                PlayListApp.YoutubeMusic -> {
                    playlistRepository.migrateToYoutubeMusic(
                        playlistName = _uiState.value.selectedPlaylist.name,
                        accessToken = playlistAccessToken.value,
                        musicIds = migrateTargetMusicIds,
                        thumbnailUrl = _uiState.value.selectedPlaylist.thumbNailUrl,
                        source = sourceName,
                        transferFailMusics = notTransferMusics
                    )
                }

                else -> {
                    //TODO 구현예정
                    flow<ApiResult.Failure> { ApiResult.Failure(-1, "구현중") }
                }
            }.collect { result ->

                result.onSuccess { _ ->
                    getMigrationProcess()
                }

                result.onFailure { code, error ->
                    sendEffect(DirectMigrationUiEffect.OnFailure)
                }
            }
        }
    }


    private fun getNotTransferMusic(): List<DestinationMusic> {
        val unSelectedSimilarMusicsIndex = mutableListOf<Int>()

        _uiState.value.selectedSimilarMusicsId.mapIndexed { index, music ->
            if (music.isBlank()) {
                unSelectedSimilarMusicsIndex.add(index)
            }
        }

        val notSelectedSimilarSourceMusics =
            _uiState.value.similarMusics.filterIndexed { index, similarMusic ->
                index in unSelectedSimilarMusicsIndex
            }.map { it.sourceMusic }

        // notFoundMusic + similarMusics에서 선택하지 않은 목록
        val notTransferMusics = _uiState.value.notFoundMusics + notSelectedSimilarSourceMusics.map {
            DestinationMusic(
                id = "",
                thumbNailUrl = it.thumbNailUrl,
                title = it.title,
                artistName = it.artistName
            )
        }
        Log.d(TAG, "getNotTransferMusic: $notTransferMusics")
        return notTransferMusics
    }

    private fun getMigrationProcess() {
        Log.d(TAG, "getMigrationProcess: launched")

        viewModelScope.launch {
            playlistRepository.getMigrationProcess().collect { result ->
                result.onSuccess { data ->
                    _uiState.update {
                        Log.d(TAG, "getMigrationProcess: $data")
                        it.copy(
                            migrationProcess = data
                        )
                    }
                    if (data.willTransferMusicCount == data.transferredMusicCount) {
                        sendEffect(DirectMigrationUiEffect.OnMigrationSuccess)
                        Log.d(TAG, "getMigrationProcess: complete migration")
                    }
                    if (data.willTransferMusicCount != data.transferredMusicCount) {
                        Log.d(TAG, "getMigrationProcess: not complete migration, retrying...")
                        launch { delay(500L) }
                        getMigrationProcess()
                    }
                }

                result.onFailure { code, error ->
                    Log.d(TAG, "getMigrationProcess: $code, $error")
                }
            }
        }
    }

    private fun getMigrationResult() {
        viewModelScope.launch {
            playlistRepository.getMigrationResult().collect { result ->
                result.onSuccess { data ->
                    _uiState.update {
                        it.copy(
                            migrationResult = data
                        )
                    }
                    getMigratedMusic(data.id)
                    getNotMigratedMusic(data.id)
                }

                result.onFailure { code, error ->
                    Log.d(TAG, "getMigrationResult: $code, $error")
                }
            }
        }
    }

    private fun getMigratedMusic(historyId: Int) {
        viewModelScope.launch {
            memberRepository.getTransferSucceedHistoryMusics(historyId).collect { result ->
                result.onSuccess { musics ->
                    _uiState.update {
                        it.copy(migratedMusics = musics)
                    }
                }
            }
        }
    }

    private fun getNotMigratedMusic(historyId: Int) {
        viewModelScope.launch {
            memberRepository.getTransferFailedHistoryMusics(historyId).collect { result ->
                result.onSuccess { musics ->
                    _uiState.update {
                        it.copy(notMigratedMusics = musics)
                    }
                }
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.update {
            it.copy(isLoading = isLoading)
        }
    }


    companion object {
        private const val TAG = "DirectMigrationViewModel"
    }
}