package com.cmc15th.pluv.feature.home.viewModel

data class HomeUiState(
    val historiesThumbnailUrl: List<Pair<Long, String>> = emptyList(),
    val savedFeedsThumbnailUrl: List<Pair<Long, String>> = emptyList()
)
