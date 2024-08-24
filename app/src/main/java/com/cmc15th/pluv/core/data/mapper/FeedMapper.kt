package com.cmc15th.pluv.core.data.mapper

import com.cmc15th.pluv.core.network.response.FeedResponse

fun FeedResponse.toFeed() = com.cmc15th.pluv.core.model.Feed(
    id = id,
    title = title,
    thumbNailUrl = thumbNailUrl,
    artistNames = artistNames,
    creatorName = creatorName
)