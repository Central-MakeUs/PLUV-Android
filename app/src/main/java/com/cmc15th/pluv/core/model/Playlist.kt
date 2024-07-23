package com.cmc15th.pluv.core.model

import com.cmc15th.pluv.domain.model.PlayListApp

data class Playlist(
    val id: String = "",
    val thumbNailUrl: String = "",
    val songCount: Int = 0,
    val name: String = "",
    val source: PlayListApp = PlayListApp.EMPTY
)
