package com.cmc15th.pluv.core.network.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("accessToken") val oauthToken: String = ""
)