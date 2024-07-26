package com.cmc15th.pluv.domain.model

enum class PlayListApp(val appName: String) {
    EMPTY(""),
    spotify("스포티파이"),
    APPLE_MUSIC("애플뮤직"),
    YOUTUBE_MUSIC("유튜브뮤직"),
    MELON("멜론");

    companion object {
        fun toAppName(appName: String): PlayListApp = enumValues<PlayListApp>().firstOrNull {
            it.appName == appName
        } ?: EMPTY

        fun getAllPlaylistApps(): List<PlayListApp> = enumValues<PlayListApp>().toList().filter {
            it != PlayListApp.EMPTY
        }
    }
}