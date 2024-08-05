package com.cmc15th.pluv.core.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val accessToken: String
)

data class GoogleAccessTokenResponse(
    @SerializedName("accessToken") val accessToken: String
)
