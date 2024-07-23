package com.cmc15th.pluv.core.network.response

import com.google.gson.annotations.SerializedName

data class ReadPlaylistResponse(
    @SerializedName("id") val id: String = "",
    @SerializedName("thumbNailUrl") val thumbNailUrl: String = "",
    @SerializedName("songCount") val songCount: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("source") val source: String = ""
)