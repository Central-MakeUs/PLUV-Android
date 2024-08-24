package com.cmc15th.pluv.core.data.mapper

import com.cmc15th.pluv.core.model.FeedInfo
import com.cmc15th.pluv.core.model.FeedMusic
import com.cmc15th.pluv.core.network.response.FeedInfoResponse
import com.cmc15th.pluv.core.network.response.FeedMusicResponse
import com.cmc15th.pluv.core.network.response.FeedResponse

fun FeedResponse.toFeed() = com.cmc15th.pluv.core.model.Feed(
    id = id,
    title = title,
    thumbNailUrl = thumbNailUrl,
    artistNames = artistNames,
    creatorName = creatorName
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

fun FeedMusicResponse.toFeedMusic() = FeedMusic(
    title = title,
    imageUrl = imageUrl,
    artistNames = artistNames
)