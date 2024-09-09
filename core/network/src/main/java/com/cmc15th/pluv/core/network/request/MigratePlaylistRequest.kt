package com.cmc15th.pluv.core.network.request

import com.google.gson.annotations.SerializedName

data class MigratePlaylistRequest(
    @SerializedName("playListName") val playlistName: String,
    @SerializedName("destinationAccessToken") val destinationAccessToken: String,
    @SerializedName("musicIds") val musicIds: List<String>,
    @SerializedName("transferFailMusics") val transferFailMusics: List<TransferFailMusics>,
    @SerializedName("thumbNailUrl") val thumbnailUrl: String,
    @SerializedName("source") val source: String
)

data class TransferFailMusics(
    @SerializedName("title") val title: String,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("imageUrl") val thumbnailUrl: String
)
