package com.cmc15th.pluv.ui.feed.viewmodel

sealed class FeedUiEvent {
    data class SelectFeed(val feedId: Long) : FeedUiEvent()
    data class ToggleBookmark(val feedId: Long) : FeedUiEvent()
}