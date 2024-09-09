package com.cmc15th.pluv.feature.feed.viewmodel

sealed class FeedUiEvent {
    data object OnLoadAllFeeds : FeedUiEvent()
    data class SelectFeed(val feedId: Long) : FeedUiEvent()
    data class ToggleBookmark(val feedId: Long) : FeedUiEvent()
    data object OnLoadSavedFeeds : FeedUiEvent()
}