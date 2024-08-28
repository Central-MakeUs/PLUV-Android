package com.cmc15th.pluv.domain.model

enum class PlayListApp(val appName: String, val sourceName: String, val playListType: PlayListType) {
    EMPTY("", "", PlayListType.SERVICE),
    Spotify("스포티파이", "spotify", PlayListType.SERVICE),
//    APPLE_MUSIC("애플뮤직"),
    YoutubeMusic("유튜브뮤직", "youtube", PlayListType.SERVICE),
    History("최근 옮긴 항목", "history", PlayListType.HISTORY),
    Feed("저장한 플레이리스트", "feed", PlayListType.FEED);
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

enum class PlayListType {
    SERVICE,
    HISTORY,
    FEED
}