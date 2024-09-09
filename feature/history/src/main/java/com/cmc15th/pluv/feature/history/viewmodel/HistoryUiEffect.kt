package com.cmc15th.pluv.feature.history.viewmodel

sealed class HistoryUiEffect {
    data class OnFailure(val message: String) : HistoryUiEffect()
}