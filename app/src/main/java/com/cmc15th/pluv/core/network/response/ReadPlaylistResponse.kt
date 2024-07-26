package com.cmc15th.pluv.core.network.response

import com.google.gson.annotations.SerializedName

data class ReadPlaylistResponse(
    @SerializedName("id") val id: String = "",
    @SerializedName("thumbNailUrl") val thumbNailUrl: String = "",
    @SerializedName("songCount") val songCount: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("source") val source: String = ""
)

data class ReadSourceMusicResponse(
    @SerializedName("name") val title: String = "",
    @SerializedName("artistNames") val artistName: String = "",
    @SerializedName("isrcCode") val isrcCode: String = "",
    @SerializedName("imageUrl") val thumbNailUrl: String = ""
)

data class ReadValidateSourceResponse(
    @SerializedName("name") val name: String = "",
    @SerializedName("artistName") val artistName: String = "",
)

data class ReadDestinationMusicResponse(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val title: String = "",
    @SerializedName("artistName") val artistName: String = "",
    @SerializedName("imageUrl") val thumbNailUrl: String = ""
)