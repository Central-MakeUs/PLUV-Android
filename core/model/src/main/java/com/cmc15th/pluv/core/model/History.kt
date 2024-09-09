package com.cmc15th.pluv.core.model

data class History(
    val id: Long = -1L,
    val transferredSongCount: Int = 0,
    val transferredDate: String = "",
    val title: String = "",
    val imageUrl: String = "",
)

data class HistoryDetail(
    val id: Long = -1L,
    val totalSongCount: Int = 0,
    val transferredSongCount: Int = 0,
    val title: String = "",
    val imageUrl: String = "",
    val source: String = "",
    val destination: String = ""
)