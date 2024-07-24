package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.Music
import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.domain.model.PlayListApp
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun fetchPlaylists(
        playlistAppName: PlayListApp,
        accessToken: String
    ): Flow<ApiResult<List<Playlist>>>

    fun fetchMusics(
        playlistAppName: PlayListApp,
        accessToken: String,
        playlistId: String
    ): Flow<ApiResult<List<Music>>>
}