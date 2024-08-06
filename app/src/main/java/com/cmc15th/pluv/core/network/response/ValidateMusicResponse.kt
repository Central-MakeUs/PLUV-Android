package com.cmc15th.pluv.core.network.response

import com.google.gson.annotations.SerializedName

data class ValidateMusicResponse(
    @SerializedName("isEqual") val isEqual: Boolean,
    @SerializedName("isFound") val isFound: Boolean,
    @SerializedName("sourceMusic") val sourceMusic: ReadValidateSourceResponse,
    @SerializedName("destinationMusic") val destinationMusic: List<ReadDestinationMusicResponse>
)
