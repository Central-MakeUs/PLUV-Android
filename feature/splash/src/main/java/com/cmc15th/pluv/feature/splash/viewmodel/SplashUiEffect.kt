package com.cmc15th.pluv.feature.splash.viewmodel

sealed class SplashUiEffect {
    data class IsLoggedIn(val state: Boolean) : SplashUiEffect()
}