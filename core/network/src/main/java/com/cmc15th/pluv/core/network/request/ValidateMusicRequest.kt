package com.cmc15th.pluv.core.network.request

import com.cmc15th.pluv.core.model.SourceMusic
import com.google.gson.annotations.SerializedName

data class ValidateMusicRequest(
    @SerializedName("destinationAccessToken") val destinationAccessToken: String,
    @SerializedName("musics") val sourceMusics: List<SourceMusic>
)
