package com.cmc15th.pluv

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.cmc15th.pluv.ui.common.WebViewScreen
import com.cmc15th.pluv.ui.feed.FeedInfoScreen
import com.cmc15th.pluv.ui.feed.FeedScreen
import com.cmc15th.pluv.ui.feed.SavedFeedScreen
import com.cmc15th.pluv.ui.feed.viewmodel.FeedViewModel
import com.cmc15th.pluv.ui.history.AllHistoryScreen
import com.cmc15th.pluv.ui.history.HistoryDetailScreen
import com.cmc15th.pluv.ui.history.viewmodel.HistoryViewModel
import com.cmc15th.pluv.ui.home.HomeScreen
import com.cmc15th.pluv.ui.home.migrate.common.screen.MigratedResultScreen
import com.cmc15th.pluv.ui.home.migrate.common.screen.MigrationProcessScreen
import com.cmc15th.pluv.ui.home.migrate.common.screen.SelectSimilarMusicScreen
import com.cmc15th.pluv.ui.home.migrate.common.screen.ShowNotFoundMusicScreen
import com.cmc15th.pluv.ui.home.migrate.direct.DirectMigrationViewModel
import com.cmc15th.pluv.ui.home.migrate.direct.DisplayMigrationPathScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectDestinationAppScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectMigratePlaylistScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectMigrationMusicScreen
import com.cmc15th.pluv.ui.home.migrate.direct.SelectSourceAppScreen
import com.cmc15th.pluv.ui.home.migrate.screenshot.UploadPlaylistScreenShotScreen
import com.cmc15th.pluv.ui.login.LoginScreen
import com.cmc15th.pluv.ui.mypage.MypageScreen
import com.cmc15th.pluv.ui.mypage.UnregisterScreen
import com.cmc15th.pluv.ui.mypage.UserInfoScreen
import com.cmc15th.pluv.ui.mypage.viewmodel.MypageViewModel
import com.cmc15th.pluv.ui.onboarding.OnboardingScreen
import com.cmc15th.pluv.ui.splash.SplashScreen

@Composable
fun PLUVNavHost(
    pluvNavController: PLUVNavController,
    showSnackBar: (String) -> Unit = {}
) {

    NavHost(
        navController = pluvNavController.navController,
        startDestination = DestinationScreens.Splash.route
//        startDestination = DestinationScreens.History.route
    ) {

        composable(route = DestinationScreens.Splash.route) { navBackStackEntry ->
            val navOptions = NavOptions.Builder().setPopUpTo(
                pluvNavController.navController.graph.findStartDestination().id,
                inclusive = true
            ).build()

            SplashScreen(
                viewModel = hiltViewModel(navBackStackEntry),
                navigateToOnboarding = {
                    pluvNavController.navigate(DestinationScreens.Onboarding.route, navOptions)
                },
                navigateToHome = {
                    pluvNavController.navigate(BottomTab.HOME.route, navOptions)
                }
            )
        }

        composable(route = DestinationScreens.Onboarding.route) { navBackStackEntry ->
            OnboardingScreen(
                navigateToLogin = {
                    pluvNavController.navigateToLogin()
                }
            )
        }

        navigation(
            route = DestinationScreens.History.route,
            startDestination = DestinationScreens.AllHistory.route
        ) {
            composable(route = DestinationScreens.AllHistory.route) { navBackStackEntry ->
                AllHistoryScreen(
                    viewModel = pluvNavController.sharedViewModel<HistoryViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    onBackClicked = {
                        pluvNavController.popBackStack()
                    },
                    navigateToHistoryDetail = {
                        pluvNavController.navigate(DestinationScreens.HistoryDetail.route)
                    }
                )
            }

            composable(route = DestinationScreens.HistoryDetail.route) { navBackStackEntry ->
                HistoryDetailScreen(
                    viewModel = pluvNavController.sharedViewModel<HistoryViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    onBackClicked = {
                        pluvNavController.popBackStack()
                    }
                )
            }
        }

        navigation(
            route = DestinationScreens.SavedFeedRoot.route,
            startDestination = DestinationScreens.SavedFeed.route
        ) {
            composable(route = DestinationScreens.SavedFeed.route) { navBackStackEntry ->
                SavedFeedScreen(
                    viewModel = pluvNavController.sharedViewModel<FeedViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    showSnackBar = showSnackBar,
                    onBackClicked = {
                        pluvNavController.popBackStack()
                    },
                    navigateToFeedDetail = {
                        pluvNavController.navigate(DestinationScreens.SavedFeedDetail.route)
                    }
                )
            }

            composable(
                route = DestinationScreens.SavedFeedDetail.route,
            ) { navBackStackEntry ->
                FeedInfoScreen(
                    viewModel = pluvNavController.sharedViewModel<FeedViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    onBackClick = {
                        pluvNavController.popBackStack()
                    },
                    showSnackBar = showSnackBar
                )
            }
        }

        composable(route = DestinationScreens.HistoryDetail.route) {
            HistoryDetailScreen()
        }

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
                },
                navigateToHistory = {
                    pluvNavController.navigate(DestinationScreens.History.route)
                },
                navigateToSavedFeed = {
                    pluvNavController.navigate(DestinationScreens.SavedFeedRoot.route)
                }
            )
        }

        navigation(
            route = DestinationScreens.Feed.route,
            startDestination = BottomTab.FEED.route
        ) {
            composable(route = BottomTab.FEED.route) { navBackStackEntry ->
                FeedScreen(
                    viewModel = pluvNavController.sharedViewModel<FeedViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    showSnackBar = showSnackBar,
                    navigateToFeedInfo = {
                        pluvNavController.navigate(DestinationScreens.FeedInfo.route)
                    }
                )
            }

            composable(route = DestinationScreens.FeedInfo.route) { navBackStackEntry ->
                FeedInfoScreen(
                    viewModel = pluvNavController.sharedViewModel<FeedViewModel>(
                        navBackStackEntry,
                    ),
                    onBackClick = {
                        pluvNavController.popBackStack()
                    },
                    showSnackBar = showSnackBar
                )
            }
        }

        navigation(
            route = DestinationScreens.Mypage.route,
            startDestination = BottomTab.MY_PAGE.route
        ) {
            composable(route = BottomTab.MY_PAGE.route) { navBackStackEntry ->
                MypageScreen(
                    viewModel = pluvNavController.sharedViewModel<MypageViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    navigateToUserInfo = {
                        pluvNavController.navigate(DestinationScreens.UserInfo.route)
                    },
                    navigateToWebView = { title, url ->
                        pluvNavController.navigate(
                            "webView?title=${Uri.encode(title)}&url=${
                                Uri.encode(
                                    url
                                )
                            }"
                        )
                    },
                    navigateToHome = {
                        pluvNavController.navigateToLogin()
                    }
                )
            }

            composable(route = DestinationScreens.UserInfo.route) { navBackStackEntry ->
                UserInfoScreen(
                    viewModel = pluvNavController.sharedViewModel<MypageViewModel>(navBackStackEntry = navBackStackEntry),
                    onBackClick = {
                        pluvNavController.popBackStack()
                    },
                    navigateToUnregister = {
                        pluvNavController.navigate(DestinationScreens.Unregister.route)
                    },
                    showSnackBar = showSnackBar
                )
            }

            composable(route = DestinationScreens.Unregister.route) { navBackStackEntry ->
                UnregisterScreen(
                    viewModel = pluvNavController.sharedViewModel<MypageViewModel>(navBackStackEntry = navBackStackEntry),
                    showSnackBar = showSnackBar,
                    onBackClicked = {
                        pluvNavController.popBackStack()
                    },
                    navigateToLogin = {
                        pluvNavController.navigateToLogin()
                    }
                )
            }
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
                        pluvNavController.navigate(
                            BottomTab.HOME.route,
                            NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build()
                        )
                    },
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    navigateToSelectDestinationApp = {
                        pluvNavController.navigate(DestinationScreens.DirectMigrationSelectDestinationApp.route)
                    }
                )
            }

            composable(route = DestinationScreens.UploadPlaylistScreenShot.route) { navBackStackEntry ->
                UploadPlaylistScreenShotScreen(
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry
                    ),
                    totalStep = totalSteps,
                    currentStep = DirectMigrationRoutes.getCurrentStep(navBackStackEntry.destination.route),
                    onCloseClick = {
                        pluvNavController.navigate(
                            BottomTab.HOME.route,
                            NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build()
                        )
                    },
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
                        pluvNavController.navigate(
                            BottomTab.HOME.route,
                            NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build()
                        )
                    },
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    navigateToSelectSource = {
                        pluvNavController.popBackStack()
                    },
                    navigateToDisplayMigrationPath = {
                        pluvNavController.navigate(DestinationScreens.ExecuteDirectMigration.route)
                    },
                    navigateToSelectMigrationMusic = {
                        pluvNavController.navigate(DestinationScreens.SelectMigrationMusic.route)
                    }
                )
            }

            composable(route = DestinationScreens.ExecuteDirectMigration.route) { navBackStackEntry ->
                val currentRoute = navBackStackEntry.destination.route
                DisplayMigrationPathScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        pluvNavController.navigate(
                            BottomTab.HOME.route,
                            NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build()
                        )
                    },
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
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
                        pluvNavController.navigate(
                            BottomTab.HOME.route,
                            NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build()
                        )
                    },
                    navigateToDisplayMigrationPath = {
                        pluvNavController.popBackStack()
                    },
                    navigateToSelectMigrationMusic = {
                        pluvNavController.navigate(DestinationScreens.SelectMigrationMusic.route)
                    },
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    )
                )
            }
            composable(route = DestinationScreens.SelectMigrationMusic.route) { navBackStackEntry ->
                val currentRoute = navBackStackEntry.destination.route
                SelectMigrationMusicScreen(
                    currentStep = DirectMigrationRoutes.getCurrentStep(currentRoute),
                    totalStep = totalSteps,
                    onCloseClick = {
                        pluvNavController.navigate(
                            BottomTab.HOME.route,
                            NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build()
                        )
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
                    onShowSnackBar = showSnackBar,
                    navigateToMigrationProcess = {
                        val navOptions = NavOptions.Builder().setPopUpTo(
                            pluvNavController.navController.graph.findStartDestination().id,
                            false
                        ).build()
                        pluvNavController.navigate(
                            DestinationScreens.MigrationProcess.route,
                            navOptions
                        )
                    },
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    )
                )
            }
            composable(route = DestinationScreens.SelectSimilarMusic.route) { navBackStackEntry ->
                SelectSimilarMusicScreen(
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    currentStep = DirectMigrationRoutes.getCurrentStep(navBackStackEntry.destination.route),
                    totalStep = totalSteps,
                    onCloseClick = {
                        pluvNavController.navigate(
                            BottomTab.HOME.route,
                            NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build()
                        )
                    },
                    onShowSnackBar = showSnackBar,

                    navigateToSelectMigrationMusic = {
                        pluvNavController.popBackStack()
                    },
                    navigateToShowNotFoundMusic = {
                        pluvNavController.navigate(DestinationScreens.ShowNotFoundMusic.route)
                    },
                    navigateToMigrationProcess = {
                        val navOptions = NavOptions.Builder().setPopUpTo(
                            pluvNavController.navController.graph.findStartDestination().id,
                            false
                        ).build()
                        pluvNavController.navigate(
                            DestinationScreens.MigrationProcess.route,
                            navOptions
                        )
                    },
                )
            }
            composable(route = DestinationScreens.ShowNotFoundMusic.route) { navBackStackEntry ->
                ShowNotFoundMusicScreen(
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    onShowSnackBar = showSnackBar,
                    onCloseClick = {
                        pluvNavController.navigate(
                            BottomTab.HOME.route,
                            NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build()
                        )
                    },
                    navigateToMigrationProcess = {
                        val navOptions = NavOptions.Builder().setPopUpTo(
                            pluvNavController.navController.graph.findStartDestination().id,
                            false
                        ).build()
                        pluvNavController.navigate(
                            DestinationScreens.MigrationProcess.route,
                            navOptions
                        )
                    },
                )
            }

            composable(route = DestinationScreens.MigrationProcess.route) { navBackStackEntry ->
                MigrationProcessScreen(
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    showSnackBar = showSnackBar,
                    navigateToHome = {
                        pluvNavController.navigate(
                            BottomTab.HOME.route,
                            NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build()
                        )
                    },
                    onCloseClicked = {
                        pluvNavController.navigate(
                            BottomTab.HOME.route,
                            NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build()
                        )
                    },
                    onStopMigrationClicked = {
                        pluvNavController.navigate(
                            BottomTab.HOME.route,
                            NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build()
                        )
                    },
                    navigateToMigrationResult = {
                        val navOptions = NavOptions.Builder().setPopUpTo(
                            pluvNavController.navController.graph.findStartDestination().id,
                            false
                        ).build()
                        pluvNavController.navigate(
                            DestinationScreens.MigratedResult.route,
                            navOptions
                        )
                    }
                )
            }

            composable(route = DestinationScreens.MigratedResult.route) { navBackStackEntry ->
                MigratedResultScreen(
                    viewModel = pluvNavController.sharedViewModel(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    showSnackBar = showSnackBar,
                    navigateToHome = {
                        pluvNavController.navigate(
                            BottomTab.HOME.route,
                            NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build()
                        )
                    }
                )
            }
        }

//        navigation(
//            route = DestinationScreens.ScreenShotMigrationRoot.route,
//            startDestination = DestinationScreens.UploadPlaylistScreenShot.route
//        ) {
//            composable(route = DestinationScreens.UploadPlaylistScreenShot.route) {
//                UploadPlaylistScreenShotScreen(
//                    onCloseClick = {
//                        pluvNavController.navigate(
//                            BottomTab.HOME.route,
//                            NavOptions.Builder().setPopUpTo(BottomTab.HOME.route, false).build()
//                        )
//                    }
//                )
//            }
//        }
    }
}

/** 플레이리스트 직접 이전하기 Navigation Route (위에 LinearProgressBar 표시하기 위한 Object) */
object DirectMigrationRoutes {

    private val selectSourceAndDestinationRoute = listOf(
        listOf(
            DestinationScreens.DirectMigrationSelectSourceApp.route,
            DestinationScreens.UploadPlaylistScreenShot.route
        ),
        DestinationScreens.DirectMigrationSelectDestinationApp.route,
        DestinationScreens.ExecuteDirectMigration.route,
        DestinationScreens.SelectMigratePlaylist.route,
        DestinationScreens.SelectMigrationMusic.route,
        DestinationScreens.SelectSimilarMusic.route,
        DestinationScreens.ShowNotFoundMusic.route
    )

    fun getRouteSize(): Int = selectSourceAndDestinationRoute.size

    fun getCurrentStep(currentRoute: String?): Int {
        selectSourceAndDestinationRoute.forEachIndexed { index, route ->
            if (route is List<*>) {
                route.forEach { subRoute ->
                    if (subRoute == currentRoute) {
                        return index + 1
                    }
                }
            }
            if (route == currentRoute) {
                return index + 1
            }
        }
        return 0
    }
}

