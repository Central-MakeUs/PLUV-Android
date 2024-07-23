package com.cmc15th.pluv.core.network.service

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.network.request.PlaylistAccessToken
import com.cmc15th.pluv.core.network.response.ReadPlaylistResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MigrationService {

    @POST("spotify/playLists/read")
    suspend fun fetchPlaylists(
        @Body accessToken: PlaylistAccessToken
    ): ApiResult<List<ReadPlaylistResponse>>

}