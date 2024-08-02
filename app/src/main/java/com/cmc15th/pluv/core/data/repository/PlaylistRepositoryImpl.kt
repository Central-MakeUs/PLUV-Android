package com.cmc15th.pluv.core.data.repository

import android.util.Log
import com.cmc15th.pluv.core.data.mapper.toPlaylist
import com.cmc15th.pluv.core.data.mapper.toSourceMusic
import com.cmc15th.pluv.core.data.mapper.toValidateMusic
import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.core.model.SourceMusic
import com.cmc15th.pluv.core.model.ValidateMusic
import com.cmc15th.pluv.core.network.request.PlaylistAccessToken
import com.cmc15th.pluv.core.network.request.ValidateMusicRequest
import com.cmc15th.pluv.core.network.service.MigrationService
import com.cmc15th.pluv.domain.model.PlayListApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val migrationService: MigrationService
) : PlaylistRepository {

    override fun fetchPlaylists(
        accessToken: String
    ): Flow<ApiResult<List<Playlist>>> = flow {
        emit(
            migrationService.fetchPlaylists(PlaylistAccessToken(accessToken))
                .map { result ->
                    result.map {
                        it.toPlaylist()
                    }
                }
        )
    }.flowOn(Dispatchers.IO)

    override fun fetchMusics(
        playlistAppName: PlayListApp,
        accessToken: String,
        playlistId: String
    ): Flow<ApiResult<List<SourceMusic>>> = flow {
        emit(
            migrationService.fetchMusicsByPlaylistId(
                playlistId = playlistId,
                accessToken = PlaylistAccessToken(accessToken)
            ).map { result ->
                result.data.map {
                    Log.d("REPOSITORY", "fetchMusics: $it ")
                    it.toSourceMusic()
                }
            }
        )
    }.flowOn(Dispatchers.IO)

    override fun validateMusic(
        playlistAppName: PlayListApp,
        accessToken: String,
        musics: List<SourceMusic>
    ): Flow<ApiResult<List<ValidateMusic>>> = flow {
        emit(
            migrationService.validateMusic(
                playlistAppName,
                ValidateMusicRequest(accessToken, musics)
            ).map { result ->
                Log.d("REPOSITORY", "validateMusic: $result")
                result.data.map {
                    it.toValidateMusic()
                }
            }
        )
    }.flowOn(Dispatchers.IO)
}