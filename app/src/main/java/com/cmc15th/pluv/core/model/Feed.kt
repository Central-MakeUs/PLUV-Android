package com.cmc15th.pluv.core.model

data class Feed(
    val id: Long = -1,
    val title: String = "",
    val thumbNailUrl: String = "",
    val artistNames: String = "",
    val creatorName: String = ""
)

data class FeedInfo(
    val id: Long = -1,
    val songCount: Int = 0,
    val title: String = "",
    val imageUrl: String = "",
    val creatorName: String = "",
    val isBookMarked: Boolean = false,
    val createdAt: String = ""
)

data class FeedMusic(
    val title: String = "",
    val imageUrl: String = "",
    val artistNames: String = ""
)