package com.cmc15th.pluv.core.network.request

import com.google.gson.annotations.SerializedName

data class GoogleLoginRequest(
    @SerializedName("idToken") val idToken: String = ""
)