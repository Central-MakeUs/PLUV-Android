package com.cmc15th.pluv

sealed class DestinationScreens(val route: String) {
    data object Home : DestinationScreens(route = "Home")
    data object SelectApp : DestinationScreens(route = "SelectApp")
    data object DirectMigrationRoot : DestinationScreens(route = "DirectMigrationRoot")
    data object DirectMigrationSelectSourceApp : DestinationScreens(route = "DirectMigrationSelectSourceApp")
    data object DirectMigrationSelectDestinationApp : DestinationScreens(route = "DirectMigrationSelectDestinationApp")
    data object ExecuteDirectMigration : DestinationScreens(route = "ExecuteDirectMigration")
    data object SelectMigratePlyListRoot : DestinationScreens(route = "SelectMigratePlyListRoot")
    data object SelectMigratePlaylist : DestinationScreens(route = "SelectMigratePlaylist")
}