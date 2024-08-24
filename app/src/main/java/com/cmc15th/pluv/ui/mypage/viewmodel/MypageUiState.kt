package com.cmc15th.pluv.ui.mypage.viewmodel

data class MypageUiState(
    val nickName: String = "음악듣는 원숭이",
    val email: List<String> = emptyList(),
    val modifiedNickName: String = "",
    val isNicknameModifying: Boolean = false,
    val isUnregisterChecked: Boolean = false,
)
