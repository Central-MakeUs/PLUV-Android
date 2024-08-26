package com.cmc15th.pluv.ui.history.viewmodel

import com.cmc15th.pluv.core.model.FeedMusic
import com.cmc15th.pluv.core.model.History
import com.cmc15th.pluv.core.model.HistoryDetail

data class HistoryUiState(
    val selectedTabIndex: Int = 0,
    val histories: List<History> = emptyList(),
    val selectedHistory: HistoryDetail = HistoryDetail(),
    val transferSuccessMusics: List<FeedMusic> = emptyList(),
    val transferFailMusics: List<FeedMusic> = emptyList(),
)