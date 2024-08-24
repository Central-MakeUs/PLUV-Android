package com.cmc15th.pluv.ui.feed.viewmodel

import com.cmc15th.pluv.core.model.Feed

data class FeedUiState(
    val allFeeds: List<Feed> = emptyList(),
    val selectedFeed: Feed = Feed(),
)