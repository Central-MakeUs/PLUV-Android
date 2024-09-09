package com.cmc15th.pluv.feature.login.viewmodel

sealed class LoginUiEffect {
    data object OnLoginSuccess : LoginUiEffect()
    data class OnLoginFailure(val message: String) : LoginUiEffect()
}