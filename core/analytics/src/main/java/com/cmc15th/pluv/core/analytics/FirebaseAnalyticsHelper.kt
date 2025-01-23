package com.cmc15th.pluv.core.analytics

import android.os.Bundle
import com.cmc15th.pluv.core.analytics.AnalyticsEvent.Companion.SCREEN_VIEW
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

internal class FirebaseAnalyticsHelper @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics,
) : AnalyticsHelper {

    override fun logEvent(event: AnalyticsEvent) {
        val params = Bundle().apply {
            event.extras.forEach {
                putString(it.key, it.value)
            }
        }
        firebaseAnalytics.logEvent(SCREEN_VIEW, params)
    }
}

