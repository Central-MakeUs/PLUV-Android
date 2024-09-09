package com.cmc15th.pluv.core.network.response

data class HistoryResponse(
    val id: Int,
    val transferredSongCount: Int,
    val transferredDate: String,
    val title: String,
    val imageUrl: String
)

data class HistoryDetailResponse(
    val id: Int,
    val totalSongCount: Int,
    val transferredSongCount: Int,
    val title: String,
    val imageUrl: String,
    val source: String,
    val destination: String
)