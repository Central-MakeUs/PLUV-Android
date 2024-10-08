package com.cmc15th.pluv.feature.mypage.viewmodel

sealed class MypageUiEffect {
    data class OnFailure(val message: String) : MypageUiEffect()
    data class OnSuccess(val message: String) : MypageUiEffect()
    data object NavigateToLogin : MypageUiEffect()
}