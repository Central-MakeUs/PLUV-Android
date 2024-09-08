package com.cmc15th.pluv.core.data.mapper

import com.cmc15th.pluv.core.model.DestinationMusic
import com.cmc15th.pluv.core.model.PlayListApp
import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.core.model.SourceMusic
import com.cmc15th.pluv.core.model.ValidateMusic
import com.cmc15th.pluv.core.network.response.ReadDestinationMusicResponse
import com.cmc15th.pluv.core.network.response.ReadPlaylistResponse
import com.cmc15th.pluv.core.network.response.ReadSourceMusicResponse
import com.cmc15th.pluv.core.network.response.ReadValidateSourceResponse
import com.cmc15th.pluv.core.network.response.ValidateMusicResponse

fun ReadPlaylistResponse.toPlaylist() = Playlist(
    id = id,
    thumbNailUrl = thumbNailUrl,
    songCount = songCount ?: 0,
    name = name,
    source = PlayListApp.toAppName(source)
)

fun ReadSourceMusicResponse.toSourceMusic() = SourceMusic(
    title = title,
    artistName = artistName ?: "",
    isrcCode = isrcCode ?: "",
    thumbNailUrl = thumbNailUrl
)

fun ReadValidateSourceResponse.toSourceMusic() = SourceMusic(
    title = title,
    artistName = artistName,
    thumbNailUrl = thumbNailUrl,
)

fun ReadDestinationMusicResponse.toDestinationMusic() = DestinationMusic(
    id = id,
    title = title,
    artistName = artistName,
    thumbNailUrl = thumbNailUrl
)

fun ValidateMusicResponse.toValidateMusic() = ValidateMusic(
    isEqual = isEqual,
    isFound = isFound,
    sourceMusic = sourceMusic.toSourceMusic(),
    destinationMusic = destinationMusics.map {
        it.toDestinationMusic()
    }
)