package com.cmc15th.pluv.core.network.request

import com.google.gson.annotations.SerializedName

data class MigratePlaylistRequest(
    @SerializedName("playListName") val playlistName: String,
    @SerializedName("destinationAccessToken") val destinationAccessToken: String,
    @SerializedName("musicIds") val musicIds: List<String>
)
