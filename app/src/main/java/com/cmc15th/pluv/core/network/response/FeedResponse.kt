package com.cmc15th.pluv.core.network.response

data class FeedResponse(
    val id: Long,
    val title: String,
    val thumbNailUrl: String,
    val artistNames: String,
    val creatorName: String
)