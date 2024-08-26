package com.cmc15th.pluv.core.data.mapper

import com.cmc15th.pluv.core.model.History
import com.cmc15th.pluv.core.model.HistoryDetail
import com.cmc15th.pluv.core.network.response.HistoryDetailResponse
import com.cmc15th.pluv.core.network.response.HistoryResponse

fun HistoryResponse.toHistory(): History = History(
    id = id,
    transferredSongCount = transferredSongCount,
    transferredDate = transferredDate,
    title = title,
    imageUrl = imageUrl
)

fun HistoryDetailResponse.toHistoryDetail(): HistoryDetail = HistoryDetail(
    id = id,
    totalSongCount = totalSongCount,
    transferredSongCount = transferredSongCount,
    title = title,
    imageUrl = imageUrl,
    source = source,
    destination = destination
)