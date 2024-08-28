package com.cmc15th.pluv.ui.history.viewmodel

import com.cmc15th.pluv.core.model.History
import com.cmc15th.pluv.core.model.HistoryDetail
import com.cmc15th.pluv.core.model.SourceMusic

data class HistoryUiState(
    val selectedTabIndex: Int = 0,
    val histories: List<History> = emptyList(),
    val selectedHistory: HistoryDetail = HistoryDetail(),
    val transferSuccessMusics: List<SourceMusic> = emptyList(),
    val transferFailMusics: List<SourceMusic> = emptyList(),
)