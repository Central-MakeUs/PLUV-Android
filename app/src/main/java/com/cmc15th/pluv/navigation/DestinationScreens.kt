package com.cmc15th.pluv.navigation

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface DestinationScreens {

    @Serializable
    data object Onboarding : DestinationScreens

    @Serializable
    data object Mypage : DestinationScreens

    @Serializable
    data object Login : DestinationScreens

    @Serializable
    data class FeedInfo(
        val feedId: Long
    ) : DestinationScreens

    @Serializable
    data object SavedFeed : DestinationScreens

    @Serializable
    data object UserInfo : DestinationScreens

    @Serializable
    data class WebView(
        val title: String,
        val url: String
    ) : DestinationScreens

    @Serializable
    data object Unregister : DestinationScreens

    @Serializable
    data object AllHistory : DestinationScreens

    @Serializable
    data class HistoryDetail(
        val historyId: Long
    ) : DestinationScreens

    @Serializable
    data object MigrationRoute : DestinationScreens {
        @Serializable
        data object DirectMigrationSelectSourceApp : DestinationScreens

        @Serializable
        data object DirectMigrationSelectDestinationApp : DestinationScreens

        @Serializable
        data object ExecuteDirectMigration : DestinationScreens

        @Serializable
        data object SelectMigratePlaylist : DestinationScreens

        @Serializable
        data object SelectMigrationMusic : DestinationScreens

        @Serializable
        data object SelectSimilarMusic : DestinationScreens

        @Serializable
        data object ShowNotFoundMusic : DestinationScreens

        @Serializable
        data object UploadPlaylistScreenShot : DestinationScreens

        @Serializable
        data object MigrationProcess : DestinationScreens

        @Serializable
        data object MigratedResult : DestinationScreens

        val routes = listOf(
            DirectMigrationSelectSourceApp,
            UploadPlaylistScreenShot,
            DirectMigrationSelectDestinationApp,
            ExecuteDirectMigration,
            SelectMigratePlaylist,
            SelectMigrationMusic,
            SelectSimilarMusic,
            ShowNotFoundMusic,
            MigrationProcess,
            MigratedResult
        )

        fun currentStep(route: DestinationScreens?): Int {
            val step = routes.indexOf(route)
            return if (step <= 1) 1
            else step + 1
        }
    }
}

@Serializable
internal sealed interface BottomTabRoute : DestinationScreens {
    @Serializable
    data object Home : BottomTabRoute

    @Serializable
    data object Feed : BottomTabRoute

    @Serializable
    data object Mypage : BottomTabRoute
}