package com.cmc15th.pluv.core.network.service

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.network.request.MigratePlaylistRequest
import com.cmc15th.pluv.core.network.request.PlaylistAccessToken
import com.cmc15th.pluv.core.network.request.ValidateMusicRequest
import com.cmc15th.pluv.core.network.response.CommonResponse
import com.cmc15th.pluv.core.network.response.HistoryDetailResponse
import com.cmc15th.pluv.core.network.response.MigrationProcessResponse
import com.cmc15th.pluv.core.network.response.ReadPlaylistResponse
import com.cmc15th.pluv.core.network.response.ReadSourceMusicResponse
import com.cmc15th.pluv.core.network.response.ValidateMusicResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MigrationService {

    @POST("/playlist/spotify/read")
    suspend fun fetchSpotifyPlaylists(
        @Body accessToken: PlaylistAccessToken
    ): ApiResult<List<ReadPlaylistResponse>>

    @POST("/playlist/youtube/read")
    suspend fun fetchYoutubeMusicPlaylists(
        @Body accessToken: PlaylistAccessToken
    ): ApiResult<List<ReadPlaylistResponse>>

    @POST("/playlist/spotify/{id}/read")
    suspend fun fetchSpotifyMusicsByPlaylistId(
        @Path("id") playlistId: String,
        @Body accessToken: PlaylistAccessToken
    ): ApiResult<CommonResponse<List<ReadSourceMusicResponse>>>

    @POST("/playlist/youtube/{id}/read")
    suspend fun fetchYoutubeMusicsByPlaylistId(
        @Path("id") playlistId: String,
        @Body accessToken: PlaylistAccessToken
    ): ApiResult<CommonResponse<List<ReadSourceMusicResponse>>>

    @POST("/music/spotify/search")
    suspend fun validateSpotifyMusic(
        @Body validateMusicRequest: ValidateMusicRequest
    ): ApiResult<CommonResponse<List<ValidateMusicResponse>>>

    @POST("/music/youtube/search")
    suspend fun validateYoutubeMusic(
        @Body validateMusicRequest: ValidateMusicRequest
    ): ApiResult<CommonResponse<List<ValidateMusicResponse>>>

    @POST("music/spotify/add")
    suspend fun migrateToSpotify(
        @Body migratePlaylistRequest: MigratePlaylistRequest
    ): ApiResult<CommonResponse<String>>

    @POST("music/youtube/add")
    suspend fun migrateToYoutubeMusic(
        @Body migratePlaylistRequest: MigratePlaylistRequest
    ): ApiResult<CommonResponse<String>>

    @GET("progress")
    suspend fun getMigrationProgress(): ApiResult<CommonResponse<MigrationProcessResponse>>

    @GET("history/recent")
    suspend fun getMigrationResult(): ApiResult<CommonResponse<HistoryDetailResponse>>
}