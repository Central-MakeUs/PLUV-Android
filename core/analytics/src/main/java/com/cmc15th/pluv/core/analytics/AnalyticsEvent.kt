package com.cmc15th.pluv.core.analytics

data class AnalyticsEvent(
    val type: String,
    val extras: List<Param> = emptyList(),
) {
    data class Param(val key: String, val value: String)

    companion object {
        const val SCREEN_NAME = "screen_name"
        const val SCREEN_VIEW = "screen_view"
    }
}