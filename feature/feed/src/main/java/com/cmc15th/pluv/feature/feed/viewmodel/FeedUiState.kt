package com.cmc15th.pluv.feature.feed.viewmodel

import com.cmc15th.pluv.core.model.Feed
import com.cmc15th.pluv.core.model.FeedInfo
import com.cmc15th.pluv.core.model.SourceMusic

data class FeedUiState(
    val isLoading: Boolean = false,
    val allFeeds: List<Feed> = emptyList(),
    val feedInfo: FeedInfo = FeedInfo(),
    val feedMusics: List<SourceMusic> = emptyList()
)