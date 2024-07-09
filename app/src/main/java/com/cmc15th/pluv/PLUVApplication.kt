package com.cmc15th.pluv

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PLUVApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}