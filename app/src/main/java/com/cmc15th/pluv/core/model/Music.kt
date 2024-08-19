package com.cmc15th.pluv.core.model

import com.google.gson.annotations.SerializedName

data class SourceMusic(
    val isrcCode: String = "",
    @SerializedName("imageUrl") val thumbNailUrl: String = "",
    val title: String = "",
    val artistName: String = "",
)

data class DestinationMusic(
    val id: String = "",
    val thumbNailUrl: String = "",
    val title: String = "",
    val artistName: String = "",
)

data class ValidateMusic(
    val isEqual: Boolean = true,
    val isFound: Boolean = true,
    val sourceMusic: SourceMusic = SourceMusic(),
    val destinationMusic: List<DestinationMusic> = emptyList()
)