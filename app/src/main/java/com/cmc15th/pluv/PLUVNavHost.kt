package com.cmc15th.pluv

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.cmc15th.pluv.ui.common.WebViewScreen
import com.cmc15th.pluv.ui.feed.FeedInfoScreen
import com.cmc15th.pluv.ui.feed.FeedScreen
import com.cmc15th.pluv.ui.home.HomeScreen
import com.cmc15th.pluv.ui.home.migrate.common.screen.MigratedResultScreen
import com.cmc15th.pluv.ui.home.migrate.common.screen.SelectSimilarMusicScreen
import com.cmc15th.pluv.ui.home.migrate.common.screen.ShowNotFoundMusicScreen
import com.cmc15th.pluv.ui.home.migrate.direct.DisplayMigrationPathScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectDestinationAppScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectMigratePlaylistScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectMigrationMusicScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectSourceAppScreen
import com.cmc15th.pluv.ui.home.migrate.screenshot.UploadPlaylistScreenShotScreen
import com.cmc15th.pluv.ui.login.LoginScreen
import com.cmc15th.pluv.ui.mypage.MypageScreen
import com.cmc15th.pluv.ui.mypage.UserInfoScreen

@Composable
fun PLUVNavHost(
    pluvNavController: PLUVNavController,
    showSnackBar: (String) -> Unit = {}
) {

    NavHost(
        navController = pluvNavController.navController,
        startDestination = BottomTab.HOME.route
    ) {

        composable(route = DestinationScreens.Login.route) { navBackStackEntry ->
            LoginScreen(
                viewModel = hiltViewModel(navBackStackEntry),
                navigateToHome = {
                    pluvNavController.navigateToBottomTab(BottomTab.HOME)
                }
            )
        }

        composable(route = BottomTab.HOME.route) {
            HomeScreen(
                navigateToDirectMigration = {
                    pluvNavController.navigate(DestinationScreens.DirectMigrationRoot.route)
                },
                navigateToScreenShotMigration = {
                    pluvNavController.navigate(DestinationScreens.UploadPlaylistScreenShot.route)
                }
            )
        }

        composable(route = BottomTab.FEED.route) {
            FeedScreen(
                navigateToFeedInfo = {
                    pluvNavController.navigate(DestinationScreens.FeedInfo.route)
                }
            )
        }

        composable(route = DestinationScreens.FeedInfo.route) {
            FeedInfoScreen(
                onBackClick = {
                    pluvNavController.popBackStack()
                },
                showSnackBar = showSnackBar
            )
        }


        composable(route = BottomTab.MY_PAGE.route) {
            MypageScreen(
                navigateToUserInfo = {
                    pluvNavController.navigate(DestinationScreens.UserInfo.route)
                },
                navigateToWebView = { title, url ->
                    pluvNavController.navigate("webView?title=${Uri.encode(title)}&url=${Uri.encode(url)}")
                }
            )
        }

        composable(route = DestinationScreens.WebView.route) { navBackStackEntry ->
            val title = navBackStackEntry.arguments?.getString("title") ?: ""
            val url = navBackStackEntry.arguments?.getString("url") ?: ""
            WebViewScreen(
                title = title,
                url = url,
                onBackClick = {
                    pluvNavController.popBackStack()
                }
            )
        }

        composable(route = DestinationScreens.UserInfo.route) {
            UserInfoScreen(
                viewModel = hiltViewModel(),
                onBackClick = {
                    pluvNavController.popBackStack()
                },
                showSnackBar = showSnackBar
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
                        pluvNavController.navigate(BottomTab.HOME.route, NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build())
                    },
                    viewModel = pluvNavController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    ),
                    navigateToSelectDestinationApp = {
                        pluvNavController.navigate(DestinationScreens.DirectMigrationSelectDestinationApp.route)
                    }
                )
            }

            composable(route = DestinationScreens.DirectMigrationSelectDestinationApp.route) { navBackStackEntry ->
                val currentRoute = navBackStackEntry.destination.route
                SelectDestinationAppScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        pluvNavController.navigate(BottomTab.HOME.route, NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build())
                    },
                    viewModel = pluvNavController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    ),
                    navigateToSelectSource = {
                        pluvNavController.popBackStack()
                    },
                    navigateToDisplayMigrationPath = {
                        pluvNavController.navigate(DestinationScreens.ExecuteDirectMigration.route)
                    }
                )
            }

            composable(route = DestinationScreens.ExecuteDirectMigration.route) { navBackStackEntry ->
                val currentRoute = navBackStackEntry.destination.route
                DisplayMigrationPathScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        pluvNavController.navigate(BottomTab.HOME.route, NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build())
                    },
                    viewModel = pluvNavController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    ),
                    navigateToSelectDestinationApp = {
                        pluvNavController.popBackStack()
                    },
                    navigateToSelectPlaylist = {
                        pluvNavController.navigate(DestinationScreens.SelectMigratePlaylist.route)
                    }
                )
            }

            composable(route = DestinationScreens.SelectMigratePlaylist.route) { navBackStackEntry ->
                val currentRoute = navBackStackEntry.destination.route
                SelectMigratePlaylistScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        pluvNavController.navigate(BottomTab.HOME.route, NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build())
                    },
                    navigateToDisplayMigrationPath = {
                        pluvNavController.popBackStack()
                    },
                    navigateToSelectMigrationMusic = {
                        pluvNavController.navigate(DestinationScreens.SelectMigrationMusic.route)
                    },
                    viewModel = pluvNavController.sharedViewModel(
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
                        pluvNavController.navigate(BottomTab.HOME.route, NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build())
                    },
                    navigateToSelectPlaylist = {
                        pluvNavController.popBackStack()
//                        navController.navigate(DestinationScreens.SelectMigratePlaylist.route)
                    },
                    navigateToSelectSimilarMusic = {
                        pluvNavController.navigate(DestinationScreens.SelectSimilarMusic.route)
                    },
                    navigateToShowNotFoundMusic = {
                        pluvNavController.navigate(DestinationScreens.ShowNotFoundMusic.route)
                    },
                    navigateToExecuteMigrationScreen = {
                        //TODO 마이그레이션 실행
                    },
                    viewModel = pluvNavController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    )
                )
            }
            composable(route = DestinationScreens.SelectSimilarMusic.route) { navBackStackEntry ->
                SelectSimilarMusicScreen(
                    viewModel = pluvNavController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    ),
                    onCloseClick = {
                        pluvNavController.navigate(BottomTab.HOME.route, NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build())
                    },
                    navigateToSelectMigrationMusic = {
                        pluvNavController.popBackStack()
                    },
                    navigateToShowNotFoundMusic = {
                        pluvNavController.navigate(DestinationScreens.ShowNotFoundMusic.route)
                    }
                )
            }
            composable(route = DestinationScreens.ShowNotFoundMusic.route) { navBackStackEntry ->
                ShowNotFoundMusicScreen(
                    viewModel = pluvNavController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    ),
                    onCloseClick = {
                        pluvNavController.navigate(BottomTab.HOME.route, NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build())
                    }
                )
            }

            composable(route = DestinationScreens.MigratedResult.route) { navBackStackEntry ->
                MigratedResultScreen(
                    viewModel = pluvNavController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                        route = DestinationScreens.DirectMigrationRoot.route
                    ),
                )
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

