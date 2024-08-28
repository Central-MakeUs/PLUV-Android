package com.cmc15th.pluv.ui.feed.viewmodel

import com.cmc15th.pluv.core.model.Feed
import com.cmc15th.pluv.core.model.FeedInfo
import com.cmc15th.pluv.core.model.FeedMusic

data class FeedUiState(
    val isLoading: Boolean = false,
    val allFeeds: List<Feed> = emptyList(),
    val feedInfo: FeedInfo = FeedInfo(),
    val feedMusics: List<FeedMusic> = emptyList()
)