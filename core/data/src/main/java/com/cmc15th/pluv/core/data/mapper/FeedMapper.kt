package com.cmc15th.pluv.core.data.mapper

import com.cmc15th.pluv.core.model.Feed
import com.cmc15th.pluv.core.model.FeedInfo
import com.cmc15th.pluv.core.model.Playlist
import com.cmc15th.pluv.core.network.response.FeedInfoResponse
import com.cmc15th.pluv.core.network.response.FeedResponse

fun FeedResponse.toFeed() = Feed(
    id = id,
    title = title,
    thumbNailUrl = thumbNailUrl,
    artistNames = artistNames,
    creatorName = creatorName,
    transferredAt = transferredAt,
    totalSongCount = totalSongCount
)

fun FeedInfoResponse.toFeedInfo() = FeedInfo(
    id = id,
    songCount = songCount,
    title = title,
    imageUrl = imageUrl,
    creatorName = creatorName,
    isBookMarked = isBookMarked,
    createdAt = createdAt
)

fun Feed.toPlaylist() = Playlist(
    id = id.toString(),
    thumbNailUrl = thumbNailUrl,
    songCount = totalSongCount,
    name = title
)