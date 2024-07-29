package com.cmc15th.pluv.ui.login.viewmodel

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

sealed class LoginUiEvent {
    data class GoogleLogin(val task: Task<GoogleSignInAccount>?): LoginUiEvent()
    data object SpotifyLogin: LoginUiEvent()
    data object AppleLogin: LoginUiEvent()
}