package com.cmc15th.pluv.domain.model

enum class PlayListApp(val appName: String, val sourceName: String, val playListAppType: PlayListAppType) {
    EMPTY("", "", PlayListAppType.SERVICE),
    Spotify("스포티파이", "spotify", PlayListAppType.SERVICE),
//    APPLE_MUSIC("애플뮤직"),
    YoutubeMusic("유튜브뮤직", "youtube", PlayListAppType.SERVICE),
    History("최근 옮긴 항목", "history", PlayListAppType.HISTORY),
    Feed("저장한 플레이리스트", "feed", PlayListAppType.FEED),
    ScreenShot("스크린샷", "screenshot", PlayListAppType.SCREEN_SHOT);
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

enum class PlayListAppType {
    SERVICE,
    HISTORY,
    FEED,
    SCREEN_SHOT
}