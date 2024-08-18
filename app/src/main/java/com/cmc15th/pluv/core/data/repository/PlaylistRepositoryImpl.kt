package com.cmc15th.pluv.core.data.repository

import android.util.Log
import com.cmc15th.pluv.core.data.mapper.toPlaylist
import com.cmc15th.pluv.core.data.mapper.toSourceMusic
import com.cmc15th.pluv.core.data.mapper.toValidateMusic
import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.core.model.SourceMusic
import com.cmc15th.pluv.core.model.ValidateMusic
import com.cmc15th.pluv.core.network.request.MigratePlaylistRequest
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

    override fun fetchSpotifyPlaylists(
        accessToken: String
    ): Flow<ApiResult<List<Playlist>>> = flow {
        emit(
            migrationService.fetchSpotifyPlaylists(PlaylistAccessToken(accessToken))
                .map { result ->
                    result.map {
                        it.toPlaylist()
                    }
                }
        )
    }.flowOn(Dispatchers.IO)

    override fun fetchYoutubeMusicPlaylists(
        accessToken: String
    ): Flow<ApiResult<List<Playlist>>> = flow {
        emit(
            migrationService.fetchYoutubeMusicPlaylists(PlaylistAccessToken(accessToken))
                .map { result ->
                    result.map {
                        it.toPlaylist()
                    }
                }
        )
    }

    override fun fetchSpotifyMusics(
        accessToken: String,
        playlistId: String
    ): Flow<ApiResult<List<SourceMusic>>> = flow {
        emit(
            migrationService.fetchSpotifyMusicsByPlaylistId(
                playlistId = playlistId,
                accessToken = PlaylistAccessToken(accessToken)
            ).map { result ->
                result.data.map {
                    it.toSourceMusic()
                }
            }
        )
    }.flowOn(Dispatchers.IO)

    override fun fetchYoutubeMusics(
        accessToken: String,
        playlistId: String
    ): Flow<ApiResult<List<SourceMusic>>> = flow {
        emit(
            migrationService.fetchYoutubeMusicsByPlaylistId(
                playlistId = playlistId,
                accessToken = PlaylistAccessToken(accessToken)
            ).map { result ->
                result.data.map {
                    it.toSourceMusic()
                }
            }
        )
    }


    override fun validateMusic(
        playlistApp: PlayListApp,
        accessToken: String,
        musics: List<SourceMusic>
    ): Flow<ApiResult<List<ValidateMusic>>> = flow {
        emit (
            when (playlistApp) {
                PlayListApp.spotify -> migrationService.validateSpotifyMusic(
                    ValidateMusicRequest(accessToken, musics)
                )

                PlayListApp.YOUTUBE_MUSIC -> migrationService.validateYoutubeMusic(
                    ValidateMusicRequest(accessToken, musics)
                )
                else -> ApiResult.Unexpected(IllegalArgumentException("Unexpected playlist app"))
            }.map { result ->
                Log.d("REPOSITORY", "validateMusic: $result")
                result.data.map {
                    it.toValidateMusic()
                }
            }
        )
    }.flowOn(Dispatchers.IO)

    override fun migrateToSpotify(
        playlistName: String,
        accessToken: String,
        musicIds: List<String>
    ): Flow<ApiResult<String>> = flow {
        emit(
            migrationService.migrateToSpotify(
                MigratePlaylistRequest(
                    playlistName = playlistName,
                    destinationAccessToken = accessToken,
                    musicIds = musicIds
                )
            ).map { result -> result.data }
        )
    }

    override fun migrateToYoutubeMusic(
        playlistName: String,
        accessToken: String,
        musicIds: List<String>
    ): Flow<ApiResult<String>> = flow {
        emit(
            migrationService.migrateToYoutubeMusic(
                MigratePlaylistRequest(
                    playlistName = playlistName,
                    destinationAccessToken = accessToken,
                    musicIds = musicIds
                )
            ).map { result -> result.data }
        )
    }
}