package com.cmc15th.pluv.ui.home.migrate.direct

import com.cmc15th.pluv.R

data class DirectMigrationUiState(
    val sourceApps: List<PlayListApp> = getAllPlaylistApps(),
    val destinationApps: List<PlayListApp> = emptyList(),
    val selectedSourceApp: PlayListApp = PlayListApp.EMPTY,
    val selectedDestinationApp: PlayListApp = PlayListApp.EMPTY,
) {
    enum class PlayListApp(
        val appIcon: Int,
        val selectedIcon: Int,
        val appName: Int
    ) {
        EMPTY(
            appIcon = R.drawable.grayplaceholder,
            selectedIcon = R.drawable.grayplaceholder,
            appName = R.string.empty
        ),
        SPOTIFY(
            appIcon = R.drawable.spotify,
            selectedIcon = R.drawable.spotify_selected,
            appName = R.string.spotify
        ),
        APPLE_MUSIC(
            appIcon = R.drawable.applemusic,
            selectedIcon = R.drawable.applemusic_selected,
            appName = R.string.apple_music
        ),
        YOUTUBE_MUSIC(
            appIcon = R.drawable.youtubemusic,
            selectedIcon = R.drawable.youtubemusic_selected,
            appName = R.string.youtube_music
        ),
        MELON(
            appIcon = R.drawable.melon,
            selectedIcon = R.drawable.melon_selected,
            appName = R.string.melon
        ),
    }

    companion object {
        fun getAllPlaylistApps(): List<PlayListApp> = enumValues<PlayListApp>().toList().filter {
            it != PlayListApp.EMPTY
        }
    }
}
