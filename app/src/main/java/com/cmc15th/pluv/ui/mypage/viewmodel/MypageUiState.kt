package com.cmc15th.pluv.ui.mypage.viewmodel

import com.cmc15th.pluv.core.model.SocialAccount

data class MypageUiState(
    val nickName: String = "음악듣는 원숭이",
    val email: List<String> = emptyList(),
    val modifiedNickName: String = "",
    val isNicknameModifying: Boolean = false,
    val integratedSocialSocialAccount: List<SocialAccount> = emptyList(),
    val isUnregisterChecked: Boolean = false,
)
