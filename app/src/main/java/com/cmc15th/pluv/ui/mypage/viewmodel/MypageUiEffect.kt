package com.cmc15th.pluv.ui.mypage.viewmodel

sealed class MypageUiEffect {
    data class OnFailure(val message: String) : MypageUiEffect()
    data object NavigateToLogin : MypageUiEffect()
}