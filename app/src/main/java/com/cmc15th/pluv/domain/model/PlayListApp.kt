package com.cmc15th.pluv.domain.model

enum class PlayListApp(val appName: String) {
    EMPTY(""),
    SPOTIFY("스포티파이"),
    APPLE_MUSIC("애플뮤직"),
    YOUTUBE_MUSIC("유튜브뮤직"),
    MELON("멜론");

    companion object {
        fun getAllPlaylistApps(): List<PlayListApp> = enumValues<PlayListApp>().toList().filter {
            it != PlayListApp.EMPTY
        }
    }
}