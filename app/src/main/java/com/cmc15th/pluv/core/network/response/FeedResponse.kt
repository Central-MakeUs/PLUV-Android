package com.cmc15th.pluv.core.network.response

data class FeedResponse(
    val id: Long,
    val title: String,
    val thumbNailUrl: String,
    val artistNames: String,
    val creatorName: String,
    val transferredAt: String
)

data class FeedInfoResponse(
    val id: Long,
    val songCount: Int,
    val title: String,
    val imageUrl: String,
    val creatorName: String,
    val isBookMarked: Boolean,
    val createdAt: String
)

data class FeedMusicResponse(
    val title: String,
    val imageUrl: String,
    val artistNames: String,
)