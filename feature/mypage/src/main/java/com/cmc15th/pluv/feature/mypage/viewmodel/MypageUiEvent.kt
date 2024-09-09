package com.cmc15th.pluv.feature.mypage.viewmodel

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.spotify.sdk.android.auth.AuthorizationResponse

sealed class MypageUiEvent {
    data object OnModifyNicknameClicked : MypageUiEvent()
    data class OnNickNameChanged(val nickname: String) : MypageUiEvent()
    data object OnChangeNicknameClicked : MypageUiEvent()
    data object OnUnRegisterMemberClicked : MypageUiEvent()
    data object OnUnregisterChecked: MypageUiEvent()
    data class OnAddGoogleAccount(val task: Task<GoogleSignInAccount>?) : MypageUiEvent()
    data class OnAddSpotifyAccount(val task: AuthorizationResponse) : MypageUiEvent()
}