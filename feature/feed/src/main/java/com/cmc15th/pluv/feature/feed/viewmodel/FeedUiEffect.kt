package com.cmc15th.pluv.feature.feed.viewmodel

sealed class FeedUiEffect {
    data class OnSaveSuccess(val message: String) : FeedUiEffect()
    data class OnFailure(val message: String) : FeedUiEffect()
}