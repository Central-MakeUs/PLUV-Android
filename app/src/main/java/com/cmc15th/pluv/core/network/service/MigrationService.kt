package com.cmc15th.pluv.core.network.service

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.network.request.PlaylistAccessToken
import com.cmc15th.pluv.core.network.request.ValidateMusicRequest
import com.cmc15th.pluv.core.network.response.CommonResponse
import com.cmc15th.pluv.core.network.response.ReadPlaylistResponse
import com.cmc15th.pluv.core.network.response.ReadSourceMusicResponse
import com.cmc15th.pluv.core.network.response.ValidateMusicResponse
import com.cmc15th.pluv.domain.model.PlayListApp
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface MigrationService {

    @POST("{source}/playLists/read")
    suspend fun fetchPlaylists(
        @Path("source") source: PlayListApp,
        @Body accessToken: PlaylistAccessToken
    ): ApiResult<List<ReadPlaylistResponse>>

    @POST("{source}/playLists/{playlistId}/read")
    suspend fun fetchMusicsByPlaylistId(
        @Path("source") source: PlayListApp,
        @Path("playlistId") playlistId: String,
        @Body accessToken: PlaylistAccessToken
    ): ApiResult<CommonResponse<List<ReadSourceMusicResponse>>>

    @POST("{source}/music/search")
    suspend fun validateMusic(
        @Path("source") source: PlayListApp,
        @Body validateMusicRequest: ValidateMusicRequest
    ): ApiResult<CommonResponse<List<ValidateMusicResponse>>>
}