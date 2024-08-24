package com.cmc15th.pluv.ui.home

import com.cmc15th.pluv.R
import com.cmc15th.pluv.domain.model.PlayListApp

fun PlayListApp.getAppIconRes(): Int {
    return when (this) {
        PlayListApp.EMPTY -> R.drawable.grayplaceholder
        PlayListApp.Spotify -> R.drawable.spotify
//        PlayListApp.APPLE_MUSIC -> R.drawable.applemusic
        PlayListApp.YoutubeMusic -> R.drawable.youtubemusic
        PlayListApp.PLUV -> R.drawable.pluv_app_logo
    }
}

fun PlayListApp.getSelectedIconRes(): Int {
    return when (this) {
        PlayListApp.EMPTY -> R.drawable.grayplaceholder
        PlayListApp.Spotify -> R.drawable.spotify_selected
//        PlayListApp.APPLE_MUSIC -> R.drawable.applemusic_selected
        PlayListApp.YoutubeMusic -> R.drawable.youtubemusic_selected
        PlayListApp.PLUV -> R.drawable.youtubemusic_selected
    }
}

fun PlayListApp.getAppNameRes(): Int {
    return when (this) {
        PlayListApp.EMPTY -> R.string.empty
        PlayListApp.Spotify -> R.string.spotify
//        PlayListApp.APPLE_MUSIC -> R.string.apple_music
        PlayListApp.YoutubeMusic -> R.string.youtube_music
        PlayListApp.PLUV -> R.string.pluv
    }
}