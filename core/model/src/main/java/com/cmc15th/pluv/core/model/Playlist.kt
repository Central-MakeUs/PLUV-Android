package com.cmc15th.pluv.core.model

data class Playlist(
    val id: String = "",
    val thumbNailUrl: String = "",
    val songCount: Int = 0,
    val name: String = "",
    val source: PlayListApp = PlayListApp.EMPTY
)
