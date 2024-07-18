package com.cmc15th.pluv

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.cmc15th.pluv.ui.home.HomeScreen
import com.cmc15th.pluv.ui.home.migrate.direct.DisplayMigrationPathScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectDestinationAppScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectMigratePlaylistScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectMigrationMusicScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectSourceAppScreen

@Composable
fun PLUVNavHost(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = DestinationScreens.Home.route
    ) {

        composable(route = DestinationScreens.Home.route) {
            HomeScreen(
                navigateToDirectMigration = {
                    navController.navigate(DestinationScreens.DirectMigrationRoot.route)
                }
            )
        }

        navigation(
            route = DestinationScreens.DirectMigrationRoot.route,
            startDestination = DestinationScreens.DirectMigrationSelectSourceApp.route
        ) {
            val totalSteps = DirectMigrationRoutes.getRouteSize()

            composable(route = DestinationScreens.DirectMigrationSelectSourceApp.route) {
                val currentRoute = navBackStackEntry?.destination?.route
                SelectSourceAppScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        navController.navigate(DestinationScreens.Home.route) {
                            popUpTo(DestinationScreens.Home.route) { inclusive = true }
                        }
                    },
                    viewModel = navController.sharedViewModel(route = DestinationScreens.DirectMigrationRoot.route),
                    navigateToSelectDestinationApp = {
                        navController.navigate(DestinationScreens.DirectMigrationSelectDestinationApp.route)
                    }
                )
            }

            composable(route = DestinationScreens.DirectMigrationSelectDestinationApp.route) {
                val currentRoute = navBackStackEntry?.destination?.route
                SelectDestinationAppScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        navController.navigate(DestinationScreens.Home.route) {
                            popUpTo(DestinationScreens.Home.route) { inclusive = true }
                        }
                    },
                    viewModel = navController.sharedViewModel(route = DestinationScreens.DirectMigrationRoot.route),
                    navigateToSelectSource = {
                        navController.navigate(DestinationScreens.DirectMigrationSelectSourceApp.route) {
                            popUpTo(DestinationScreens.DirectMigrationSelectSourceApp.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToDisplayMigrationPath = {
                        navController.navigate(DestinationScreens.ExecuteDirectMigration.route)
                    }
                )
            }

            composable(route = DestinationScreens.ExecuteDirectMigration.route) {
                val currentRoute = navBackStackEntry?.destination?.route
                DisplayMigrationPathScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        navController.navigate(DestinationScreens.Home.route) {
                            popUpTo(DestinationScreens.Home.route) { inclusive = true }
                        }
                    },
                    viewModel = navController.sharedViewModel(route = DestinationScreens.DirectMigrationRoot.route),
                    navigateToSelectDestinationApp = {
                        navController.navigate(DestinationScreens.DirectMigrationSelectDestinationApp.route) {
                            popUpTo(DestinationScreens.DirectMigrationSelectDestinationApp.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToSelectPlaylist = {
                        navController.navigate(DestinationScreens.SelectMigratePlaylist.route)
                    }
                )

            }

            composable(route = DestinationScreens.SelectMigratePlaylist.route) {
                val currentRoute = navBackStackEntry?.destination?.route
                SelectMigratePlaylistScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        navController.navigate(DestinationScreens.Home.route) {
                            popUpTo(DestinationScreens.Home.route) { inclusive = true }
                        }
                    },
                    navigateToDisplayMigrationPath = {
                        navController.navigate(DestinationScreens.ExecuteDirectMigration.route) {
                            popUpTo(DestinationScreens.ExecuteDirectMigration.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToSelectMigrationMusic = {
                        navController.navigate(DestinationScreens.SelectMigrationMusic.route)
                    },
                    viewModel = navController.sharedViewModel(route = DestinationScreens.DirectMigrationRoot.route)
                )
            }
            composable(route = DestinationScreens.SelectMigrationMusic.route) {
                val currentRoute = navBackStackEntry?.destination?.route
                SelectMigrationMusicScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        navController.navigate(DestinationScreens.Home.route) {
                            popUpTo(DestinationScreens.Home.route) { inclusive = true }
                        }
                    },
                    navigateToSelectPlaylist = {
                        navController.navigate(DestinationScreens.SelectMigratePlaylist.route)
                    },
                    navigateToExecuteMigrationScreen = {

                    },
                    viewModel = navController.sharedViewModel(route = DestinationScreens.DirectMigrationRoot.route)
                )
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavController.sharedViewModel(route: String): T {
    val parentEntry = remember {
        getBackStackEntry(route)
    }
    return hiltViewModel(parentEntry)
}

/** 플레이리스트 직접 이전하기 Navigation Route (위에 LinearProgressBar 표시하기 위한 Object) */
object DirectMigrationRoutes {

    private val selectSourceAndDestinationRoute = listOf(
        DestinationScreens.DirectMigrationSelectSourceApp.route,
        DestinationScreens.DirectMigrationSelectDestinationApp.route,
        DestinationScreens.ExecuteDirectMigration.route
    )

    private val directMigrationRoutes = listOf(
        selectSourceAndDestinationRoute,
        listOf(
            DestinationScreens.SelectMigratePlaylist.route
        ),
        listOf(
            DestinationScreens.SelectMigrationMusic.route
        )
    )

    fun getRouteSize(): Int = directMigrationRoutes.size

    fun getCurrentStep(currentRoute: String?): Int {
        directMigrationRoutes.forEachIndexed { index, routeList ->
            if (routeList.contains(currentRoute)) {
                return index + 1  // 단계는 1부터 시작
            }
        }
        return 0
    }
}

