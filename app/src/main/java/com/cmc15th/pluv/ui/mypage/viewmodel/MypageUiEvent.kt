package com.cmc15th.pluv.ui.mypage.viewmodel

sealed class MypageUiEvent {
    data object OnModifyNicknameClicked : MypageUiEvent()
    data class OnNickNameChanged(val nickname: String) : MypageUiEvent()
    data object OnChangeNicknameClicked : MypageUiEvent()
}