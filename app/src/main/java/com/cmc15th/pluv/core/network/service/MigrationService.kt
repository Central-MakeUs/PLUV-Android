package com.cmc15th.pluv.core.network.service

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.network.request.PlaylistAccessToken
import com.cmc15th.pluv.core.network.response.ReadPlaylistResponse
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

}