package com.cmc15th.pluv.core.network.response

import com.cmc15th.pluv.core.model.SocialAccount
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val accessToken: String
)

data class GoogleAccessTokenResponse(
    @SerializedName("accessToken") val accessToken: String
)

data class LoginTypeResponse(
    @SerializedName("type") val type: SocialAccount
)
