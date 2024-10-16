package com.cmc15th.pluv.core.model

data class SourceMusic(
    val isrcCode: String = "",
    val imageUrl: String = "",
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