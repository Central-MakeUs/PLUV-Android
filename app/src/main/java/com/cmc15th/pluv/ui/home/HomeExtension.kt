package com.cmc15th.pluv.ui.home

import com.cmc15th.pluv.R
import com.cmc15th.pluv.domain.model.PlayListApp

fun PlayListApp.getAppIconRes(): Int {
    return when (this) {
        PlayListApp.EMPTY -> R.drawable.grayplaceholder
        PlayListApp.SPOTIFY -> R.drawable.spotify
//        PlayListApp.APPLE_MUSIC -> R.drawable.applemusic
        PlayListApp.YOUTUBE_MUSIC -> R.drawable.youtubemusic
    }
}

fun PlayListApp.getSelectedIconRes(): Int {
    return when (this) {
        PlayListApp.EMPTY -> R.drawable.grayplaceholder
        PlayListApp.SPOTIFY -> R.drawable.spotify_selected
//        PlayListApp.APPLE_MUSIC -> R.drawable.applemusic_selected
        PlayListApp.YOUTUBE_MUSIC -> R.drawable.youtubemusic_selected
    }
}

fun PlayListApp.getAppNameRes(): Int {
    return when (this) {
        PlayListApp.EMPTY -> R.string.empty
        PlayListApp.SPOTIFY -> R.string.spotify
//        PlayListApp.APPLE_MUSIC -> R.string.apple_music
        PlayListApp.YOUTUBE_MUSIC -> R.string.youtube_music
    }
}