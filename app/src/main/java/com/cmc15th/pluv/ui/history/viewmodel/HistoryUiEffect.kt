package com.cmc15th.pluv.ui.history.viewmodel

sealed class HistoryUiEffect {
    data class OnFailure(val message: String) : HistoryUiEffect()
}