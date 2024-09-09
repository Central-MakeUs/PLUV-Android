package com.cmc15th.pluv.feature.history.viewmodel

sealed class HistoryUiEvent {
    data object OnLoadHistories: HistoryUiEvent()
    data class OnHistoryClicked(val historyId: Int) : HistoryUiEvent()
}