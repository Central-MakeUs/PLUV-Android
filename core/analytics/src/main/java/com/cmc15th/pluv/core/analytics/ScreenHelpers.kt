package com.cmc15th.pluv.core.analytics

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAnalyticsHelper = staticCompositionLocalOf<AnalyticsHelper> {
    error("No AnalyticsHelper provided")
}