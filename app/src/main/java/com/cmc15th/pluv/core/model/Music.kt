package com.cmc15th.pluv.core.model

data class SourceMusic(
    val isrcCode: String = "",
    val thumbNailUrl: String = "",
    val title: String? = "",
    val artistName: String = "",
)

data class DestinationMusic(
    val id: String = "",
    val thumbNailUrl: String = "",
    val title: String = "",
    val artistName: String = "",
)

data class ValidateMusic(
    val isEqual: Boolean,
    val isFound: Boolean,
    val sourceMusic: SourceMusic,
    val destinationMusic: DestinationMusic
)