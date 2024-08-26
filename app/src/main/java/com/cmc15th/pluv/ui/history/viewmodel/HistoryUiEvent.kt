package com.cmc15th.pluv.ui.history.viewmodel

sealed class HistoryUiEvent {
    data class OnHistoryClicked(val historyId: Int) : HistoryUiEvent()
}