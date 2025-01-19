package com.cmc15th.pluv.core.network.request

import com.google.gson.annotations.SerializedName

data class GoogleLoginRequest(
    @SerializedName("idToken") val idToken: String = ""
)

data class SpotifyLoginRequest(
    @SerializedName("accessToken") val accessToken: String = ""
)

data class TestLoginRequest(
    @SerializedName("id") val id: String = "",
    @SerializedName("password") val password: String = ""
)