package com.cmc15th.pluv.core.data.mapper

import com.cmc15th.pluv.core.model.Music
import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.core.network.response.ReadPlaylistResponse
import com.cmc15th.pluv.domain.model.PlayListApp

fun ReadPlaylistResponse.toPlaylist() = Playlist(
    id = id,
    thumbNailUrl = thumbNailUrl,
    songCount = songCount,
    name = name,
    source = PlayListApp.toAppName(source)
)

fun ReadPlaylistResponse.ReadMusicResponse.toMusic() = Music(
    title = title,
    artistName = artistName,
    isrcCode = isrcCode,
    thumbNailUrl = thumbNailUrl
)