package com.cmc15th.pluv.ui.login.viewmodel

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.spotify.sdk.android.auth.AuthorizationResponse

sealed class LoginUiEvent {
    data class GoogleLogin(val task: Task<GoogleSignInAccount>?): LoginUiEvent()
    data class SpotifyLogin(val task: AuthorizationResponse) : LoginUiEvent()
    data object AppleLogin: LoginUiEvent()
}