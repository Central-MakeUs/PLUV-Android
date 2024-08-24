package com.cmc15th.pluv.ui.feed.viewmodel

sealed class FeedUiEffect {
    data class OnFailure(val message: String) : FeedUiEffect()
}