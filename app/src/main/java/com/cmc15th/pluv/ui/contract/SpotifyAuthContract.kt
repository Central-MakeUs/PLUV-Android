package com.cmc15th.pluv.ui.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.cmc15th.pluv.BuildConfig
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

class SpotifyAuthContract : ActivityResultContract<Int, AuthorizationResponse>() {

    override fun createIntent(context: Context, input: Int): Intent {
        AuthorizationClient.clearCookies(context)
        val authRequest = AuthorizationRequest.Builder(
            BuildConfig.spotify_client_id,
            AuthorizationResponse.Type.TOKEN,
            BuildConfig.spotify_redirect_uri
        ).apply {
            setScopes(arrayOf("user-read-private", "playlist-read"))
        }.build()

        return AuthorizationClient.createLoginActivityIntent(context as Activity, authRequest)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): AuthorizationResponse {
        return AuthorizationClient.getResponse(resultCode, intent)
    }

}