package com.cmc15th.pluv.feature.home.viewModel

sealed class HomeUiEvent {
    data object OnLoadHistories: HomeUiEvent()
    data object OnLoadSavedFeeds: HomeUiEvent()
}