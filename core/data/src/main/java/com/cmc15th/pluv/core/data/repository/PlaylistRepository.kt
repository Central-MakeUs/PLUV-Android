package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.DestinationMusic
import com.cmc15th.pluv.core.model.HistoryDetail
import com.cmc15th.pluv.core.model.MigrationProcess
import com.cmc15th.pluv.core.model.PlayListApp
import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.core.model.SourceMusic
import com.cmc15th.pluv.core.model.ValidateMusic
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun fetchScreenshotPlaylist(
        base64EncodedImages: List<String>
    ): Flow<ApiResult<List<SourceMusic>>>

    fun fetchSpotifyPlaylists(
        accessToken: String
    ): Flow<ApiResult<List<Playlist>>>

    fun fetchYoutubeMusicPlaylists(
        accessToken: String
    ): Flow<ApiResult<List<Playlist>>>

    fun fetchSpotifyMusics(
        accessToken: String,
        playlistId: String
    ): Flow<ApiResult<List<SourceMusic>>>

    fun fetchYoutubeMusics(
        accessToken: String,
        playlistId: String
    ): Flow<ApiResult<List<SourceMusic>>>

    fun validateMusic(
        playlistAppName: PlayListApp,
        accessToken: String,
        musics: List<SourceMusic>
    ): Flow<ApiResult<List<ValidateMusic>>>

    fun migrateToSpotify(
        playlistName: String,
        accessToken: String,
        musicIds: List<String>,
        transferFailMusics: List<DestinationMusic>,
        thumbnailUrl: String,
        source: String
    ): Flow<ApiResult<String>>

    fun migrateToYoutubeMusic(
        playlistName: String,
        accessToken: String,
        musicIds: List<String>,
        transferFailMusics: List<DestinationMusic>,
        thumbnailUrl: String,
        source: String
    ): Flow<ApiResult<String>>

    fun getMigrationProcess(): Flow<ApiResult<MigrationProcess>>

    fun getMigrationResult(): Flow<ApiResult<HistoryDetail>>
}