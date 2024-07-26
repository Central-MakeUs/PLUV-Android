package com.cmc15th.pluv

import PlaylistLoginScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.cmc15th.pluv.domain.model.PlayListApp
import com.cmc15th.pluv.ui.home.HomeScreen
import com.cmc15th.pluv.ui.home.migrate.common.screen.SelectSimilarMusicScreen
import com.cmc15th.pluv.ui.home.migrate.direct.DisplayMigrationPathScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectDestinationAppScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectMigratePlaylistScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectMigrationMusicScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectSourceAppScreen
import com.cmc15th.pluv.ui.home.migrate.screenshot.UploadPlaylistScreenShotScreen

@Composable
fun PLUVNavHost(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = DestinationScreens.Home.route
    ) {

        composable(route = DestinationScreens.Home.route) {
            HomeScreen(
                navigateToDirectMigration = {
                    navController.navigate(DestinationScreens.DirectMigrationRoot.route)
                },
                navigateToScreenShotMigration = {
                    navController.navigate(DestinationScreens.UploadPlaylistScreenShot.route)
                }
            )
        }


        navigation(
            route = DestinationScreens.DirectMigrationRoot.route,
            startDestination = DestinationScreens.DirectMigrationSelectSourceApp.route
        ) {
            val totalSteps = DirectMigrationRoutes.getRouteSize()

            composable(route = DestinationScreens.DirectMigrationSelectSourceApp.route) { navBackStackEntry ->
                val currentRoute = navBackStackEntry.destination.route
                SelectSourceAppScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        navController.navigate(DestinationScreens.Home.route) {
                            popUpTo(DestinationScreens.Home.route) { inclusive = true }
                        }
                    },
                    viewModel = navController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    ),
                    navigateToSelectDestinationApp = {
                        navController.navigate(DestinationScreens.DirectMigrationSelectDestinationApp.route)
                    }
                )
            }

            composable(route = DestinationScreens.DirectMigrationSelectDestinationApp.route) { navBackStackEntry ->
                val currentRoute = navBackStackEntry.destination.route
                SelectDestinationAppScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        navController.navigate(DestinationScreens.Home.route) {
                            popUpTo(DestinationScreens.Home.route) { inclusive = true }
                        }
                    },
                    viewModel = navController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    ),
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

            composable(route = DestinationScreens.ExecuteDirectMigration.route) { navBackStackEntry ->
                val currentRoute = navBackStackEntry.destination.route
                DisplayMigrationPathScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        navController.navigate(DestinationScreens.Home.route) {
                            popUpTo(DestinationScreens.Home.route) { inclusive = true }
                        }
                    },
                    viewModel = navController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    ),
                    navigateToSelectDestinationApp = {
                        navController.navigate(DestinationScreens.DirectMigrationSelectDestinationApp.route) {
                            popUpTo(DestinationScreens.DirectMigrationSelectDestinationApp.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToSelectPlaylist = {
                        navController.navigate(DestinationScreens.SelectMigratePlaylist.route)
                    },
                    navigateToLoginSourceApp = { sourceApp ->
                        when (sourceApp) {
                            PlayListApp.spotify -> navController.navigate(DestinationScreens.SpotifyLogin.route)
                            else -> {
                                //TODO 애플뮤직, 유튜브뮤직, 멜론 추가 예정
                            }
                        }
                    }
                )
            }

            composable(route = DestinationScreens.SpotifyLogin.route) { navBackStackEntry ->
                PlaylistLoginScreen(
                    viewModel = navController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    ),
                    navigateToPreviousScreen = {
                        navController.popBackStack()
                    },
                    onLoginError = {
                        //TODO 에러 스낵바 표시
                        navController.navigate(DestinationScreens.DirectMigrationSelectDestinationApp.route) {
                            popUpTo(DestinationScreens.DirectMigrationSelectDestinationApp.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(route = DestinationScreens.SelectMigratePlaylist.route) { navBackStackEntry ->
                val currentRoute = navBackStackEntry.destination.route
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
                    viewModel = navController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    )
                )
            }
            composable(route = DestinationScreens.SelectMigrationMusic.route) { navBackStackEntry ->
                val currentRoute = navBackStackEntry.destination.route
                SelectMigrationMusicScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        navController.navigate(DestinationScreens.Home.route) {
                            popUpTo(DestinationScreens.Home.route) { inclusive = true }
                        }
                    },
                    navigateToLoginScreen = { destinationApp ->
                        //TODO 다른 서비스 로그인 구현시 사용예정
//                        when (destinationApp) {
//                            PlayListApp.spotify -> navController.navigate(DestinationScreens.SpotifyLogin.route)
//                            else -> {
//                                //TODO 애플뮤직, 유튜브뮤직, 멜론 추가 예정
//                            }
//                        }
                        navController.navigate(DestinationScreens.SpotifyLogin.route)
                    },
                    navigateToSelectPlaylist = {
                        navController.popBackStack()
//                        navController.navigate(DestinationScreens.SelectMigratePlaylist.route)
                    },
                    navigateToSelectSimilarMusic = {
                        navController.navigate(DestinationScreens.SelectSimilarMusic.route)
                    },
                    navigateToShowNotFoundMusic = {
                        navController.navigate(DestinationScreens.ShowNotFoundMusic.route)
                    },
                    navigateToExecuteMigrationScreen = {
                        //TODO 마이그레이션 실행
                    },
                    viewModel = navController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    )
                )
            }
            composable(route = DestinationScreens.SelectSimilarMusic.route) { navBackStackEntry ->
                SelectSimilarMusicScreen(
                    viewModel = navController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    )
                )
            }
            composable(route = DestinationScreens.ShowNotFoundMusic.route) { navBackStackEntry ->
                //TODO 찾을 수 없는 음악 화면
            }
        }
        navigation(
            route = DestinationScreens.ScreenShotMigrationRoot.route,
            startDestination = DestinationScreens.UploadPlaylistScreenShot.route
        ) {
            composable(route = DestinationScreens.UploadPlaylistScreenShot.route) {
                UploadPlaylistScreenShotScreen()
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavController.sharedViewModel(
    navBackStackEntry: NavBackStackEntry,
    route: String
): T {
    val parentEntry = remember(navBackStackEntry) {
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

