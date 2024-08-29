package com.cmc15th.pluv.ui.splash.viewmodel

sealed class SplashUiEffect {
    data class IsLoggedIn(val state: Boolean) : SplashUiEffect()
}