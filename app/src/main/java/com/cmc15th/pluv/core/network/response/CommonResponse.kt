package com.cmc15th.pluv.core.network.response

import com.google.gson.annotations.SerializedName

data class CommonResponse<out T>(
    @SerializedName("code") val code: Int,
    @SerializedName("msg") val message: String,
    @SerializedName("data") val data: T
)
