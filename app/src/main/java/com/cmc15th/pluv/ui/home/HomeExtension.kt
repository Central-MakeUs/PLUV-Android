package com.cmc15th.pluv.ui.home

import com.cmc15th.pluv.R
import com.cmc15th.pluv.domain.model.PlayListApp

fun PlayListApp.getAppIconRes(): Int {
    return when (this) {
        PlayListApp.EMPTY -> R.drawable.grayplaceholder
        PlayListApp.Spotify -> R.drawable.spotify
//        PlayListApp.APPLE_MUSIC -> R.drawable.applemusic
        PlayListApp.YoutubeMusic -> R.drawable.youtubemusic
        PlayListApp.History -> R.drawable.direct_icon
        PlayListApp.Feed -> R.drawable.feed_icon
        PlayListApp.ScreenShot -> R.drawable.screenshot_migration_selected
    }
}

fun PlayListApp.getSelectedIconRes(): Int {
    return when (this) {
        PlayListApp.EMPTY -> R.drawable.grayplaceholder
        PlayListApp.Spotify -> R.drawable.spotify_selected
//        PlayListApp.APPLE_MUSIC -> R.drawable.applemusic_selected
        PlayListApp.YoutubeMusic -> R.drawable.youtubemusic_selected
        PlayListApp.History -> R.drawable.direct_icon
        PlayListApp.Feed -> R.drawable.feed_icon
        PlayListApp.ScreenShot -> R.drawable.screenshot_migration_selected
    }
}

fun PlayListApp.getAppNameRes(): Int {
    return when (this) {
        PlayListApp.EMPTY -> R.string.empty
        PlayListApp.Spotify -> R.string.spotify
//        PlayListApp.APPLE_MUSIC -> R.string.apple_music
        PlayListApp.YoutubeMusic -> R.string.youtube_music
        PlayListApp.History -> R.string.history
        PlayListApp.Feed -> R.string.saved_feed
        PlayListApp.ScreenShot -> R.string.screenshot
    }
}