package com.cmc15th.pluv.domain.model

enum class PlayListApp {
    EMPTY,
    SPOTIFY,
    APPLE_MUSIC,
    YOUTUBE_MUSIC,
    MELON;

    companion object {
        fun getAllPlaylistApps(): List<PlayListApp> = enumValues<PlayListApp>().toList().filter {
            it != PlayListApp.EMPTY
        }
    }
}