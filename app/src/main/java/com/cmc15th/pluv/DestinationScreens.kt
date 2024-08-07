package com.cmc15th.pluv

sealed class DestinationScreens(val route: String) {
    data object Login : DestinationScreens(route = "Login")
    data object DirectMigrationRoot : DestinationScreens(route = "DirectMigrationRoot")
    data object DirectMigrationSelectSourceApp : DestinationScreens(route = "DirectMigrationSelectSourceApp")
    data object DirectMigrationSelectDestinationApp : DestinationScreens(route = "DirectMigrationSelectDestinationApp")
    data object ExecuteDirectMigration : DestinationScreens(route = "ExecuteDirectMigration")
    data object SelectMigratePlaylist : DestinationScreens(route = "SelectMigratePlaylist")
    data object SelectMigrationMusic : DestinationScreens(route = "SelectMigrationMusic")
    data object SelectSimilarMusic : DestinationScreens(route = "SelectSimilarMusic")
    data object ShowNotFoundMusic : DestinationScreens(route = "ShowNotFoundMusic")
    data object ScreenShotMigrationRoot : DestinationScreens(route = "ScreenShotMigrationRoot")
    data object UploadPlaylistScreenShot : DestinationScreens(route = "UploadPlaylistScreenShot")
    data object MigratedResult : DestinationScreens(route = "MigratedResult")
}