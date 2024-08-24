package com.cmc15th.pluv.domain.model

enum class PlayListApp(val appName: String, val sourceName: String) {
    EMPTY("", ""),
    Spotify("스포티파이", "spotify"),
//    APPLE_MUSIC("애플뮤직"),
    YoutubeMusic("유튜브뮤직", "youtube"),
    PLUV("플럽", "pluv");
//    MELON("멜론");

    companion object {
        fun toAppName(appName: String): PlayListApp = enumValues<PlayListApp>().firstOrNull {
            it.appName == appName
        } ?: EMPTY

        fun getAllPlaylistApps(): List<PlayListApp> = enumValues<PlayListApp>().toList().filter {
            it != PlayListApp.EMPTY
        }
    }
}