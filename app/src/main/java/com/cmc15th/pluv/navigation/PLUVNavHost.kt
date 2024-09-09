package com.cmc15th.pluv.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.cmc15th.pluv.feature.common.WebViewScreen
import com.cmc15th.pluv.feature.feed.FeedInfoScreen
import com.cmc15th.pluv.feature.feed.FeedScreen
import com.cmc15th.pluv.feature.feed.SavedFeedScreen
import com.cmc15th.pluv.feature.feed.viewmodel.FeedViewModel
import com.cmc15th.pluv.feature.history.AllHistoryScreen
import com.cmc15th.pluv.feature.history.HistoryDetailScreen
import com.cmc15th.pluv.feature.home.HomeScreen
import com.cmc15th.pluv.feature.login.LoginScreen
import com.cmc15th.pluv.feature.migrate.common.screen.MigratedResultScreen
import com.cmc15th.pluv.feature.migrate.common.screen.MigrationProcessScreen
import com.cmc15th.pluv.feature.migrate.common.screen.SelectSimilarMusicScreen
import com.cmc15th.pluv.feature.migrate.common.screen.ShowNotFoundMusicScreen
import com.cmc15th.pluv.feature.migrate.direct.DisplayMigrationPathScreen
import com.cmc15th.pluv.feature.migrate.direct.SelectDestinationAppScreen
import com.cmc15th.pluv.feature.migrate.direct.SelectMigratePlaylistScreen
import com.cmc15th.pluv.feature.migrate.direct.SelectMigrationMusicScreen
import com.cmc15th.pluv.feature.migrate.direct.SelectSourceAppScreen
import com.cmc15th.pluv.feature.migrate.screenshot.UploadPlaylistScreenShotScreen
import com.cmc15th.pluv.feature.migrate.viewmodel.DirectMigrationViewModel
import com.cmc15th.pluv.feature.mypage.MypageScreen
import com.cmc15th.pluv.feature.mypage.UnregisterScreen
import com.cmc15th.pluv.feature.mypage.UserInfoScreen
import com.cmc15th.pluv.feature.mypage.viewmodel.MypageViewModel
import com.cmc15th.pluv.feature.onboarding.OnboardingScreen
import com.cmc15th.pluv.feature.splash.SplashScreen

@Composable
fun PLUVNavHost(
    pluvNavController: PLUVNavController,
    showSnackBar: (String) -> Unit = {}
) {

    NavHost(
        navController = pluvNavController.navController,
        startDestination = DestinationScreens.Splash
    ) {
        composable<DestinationScreens.Splash> { navBackStackEntry ->
            val navOptions = NavOptions.Builder().setPopUpTo(
                pluvNavController.navController.graph.findStartDestination().id,
                inclusive = true
            ).build()

            SplashScreen(
                viewModel = hiltViewModel(navBackStackEntry),
                navigateToOnboarding = {
                    pluvNavController.navigate(DestinationScreens.Onboarding, navOptions)
                },
                navigateToHome = {
                    pluvNavController.navigate(BottomTabRoute.Home)
                }
            )
        }

        composable<DestinationScreens.Onboarding> { navBackStackEntry ->
            OnboardingScreen(
                navigateToLogin = {
                    pluvNavController.navigateToLogin()
                }
            )
        }

        composable<DestinationScreens.AllHistory> { navBackStackEntry ->
            AllHistoryScreen(
                viewModel = hiltViewModel(navBackStackEntry),
                onBackClicked = {
                    pluvNavController.popBackStack()
                },
                navigateToHistoryDetail = { historyId ->
                    pluvNavController.navigate(DestinationScreens.HistoryDetail(historyId))
                }
            )
        }

        composable<DestinationScreens.HistoryDetail> { navBackStackEntry ->
            val historyId = navBackStackEntry.toRoute<DestinationScreens.HistoryDetail>().historyId
            HistoryDetailScreen(
                historyId = historyId,
                viewModel = hiltViewModel(navBackStackEntry),
                onBackClicked = {
                    pluvNavController.popBackStack()
                }
            )
        }

        composable<DestinationScreens.SavedFeed> { navBackStackEntry ->
            SavedFeedScreen(
                viewModel = pluvNavController.sharedViewModel<FeedViewModel>(
                    navBackStackEntry = navBackStackEntry,
                ),
                showSnackBar = showSnackBar,
                onBackClicked = {
                    pluvNavController.popBackStack()
                },
                navigateToFeedDetail = { feedId ->
                    pluvNavController.navigate(
                        DestinationScreens.FeedInfo(feedId)
                    )
                }
            )
        }

        composable<DestinationScreens.Login> { navBackStackEntry ->
            LoginScreen(
                viewModel = hiltViewModel(navBackStackEntry),
                navigateToHome = {
                    pluvNavController.navigateToBottomTab(BottomTab.HOME)
                }
            )
        }

        composable<BottomTabRoute.Home> {
            HomeScreen(
                navigateToDirectMigration = {
                    pluvNavController.navigate(DestinationScreens.DirectMigrationRoot)
                },
                navigateToScreenShotMigration = {
                    pluvNavController.navigate(DestinationScreens.UploadPlaylistScreenShot)
                },
                navigateToHistory = {
                    pluvNavController.navigate(DestinationScreens.AllHistory)
                },
                navigateToSavedFeed = {
                    pluvNavController.navigate(DestinationScreens.SavedFeed)
                }
            )
        }

        composable<BottomTabRoute.Feed> { navBackStackEntry ->
            FeedScreen(
                viewModel = hiltViewModel(navBackStackEntry),
                showSnackBar = showSnackBar,
                navigateToFeedInfo = { feedId ->
                    pluvNavController.navigate(DestinationScreens.FeedInfo(feedId))
                }
            )
        }

        composable<DestinationScreens.FeedInfo> { navBackStackEntry ->
            val feedId = navBackStackEntry.toRoute<DestinationScreens.FeedInfo>().feedId
            FeedInfoScreen(
                feedId = feedId,
                viewModel = hiltViewModel(navBackStackEntry),
                onBackClick = {
                    pluvNavController.popBackStack()
                },
                showSnackBar = showSnackBar
            )
        }


        navigation<DestinationScreens.Mypage>(
            startDestination = BottomTabRoute.Mypage
        ) {
            composable<BottomTabRoute.Mypage> { navBackStackEntry ->
                MypageScreen(
                    viewModel = pluvNavController.sharedViewModel<MypageViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    navigateToUserInfo = {
                        pluvNavController.navigate(DestinationScreens.UserInfo)
                    },
                    navigateToWebView = { title, url ->
                        pluvNavController.navigate(
                            DestinationScreens.WebView(
                                title = title,
                                url = url
                            )
                        )
                    },
                    navigateToHome = {
                        pluvNavController.navigateToLogin()
                    }
                )
            }

            composable<DestinationScreens.UserInfo> { navBackStackEntry ->
                UserInfoScreen(
                    viewModel = pluvNavController.sharedViewModel<MypageViewModel>(navBackStackEntry = navBackStackEntry),
                    onBackClick = {
                        pluvNavController.popBackStack()
                    },
                    navigateToUnregister = {
                        pluvNavController.navigate(DestinationScreens.Unregister)
                    },
                    showSnackBar = showSnackBar
                )
            }

            composable<DestinationScreens.Unregister> { navBackStackEntry ->
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


        composable<DestinationScreens.WebView> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<DestinationScreens.WebView>()
            val title = args.title
            val url = args.url

            WebViewScreen(
                title = title,
                url = url,
                onBackClick = {
                    pluvNavController.popBackStack()
                }
            )
        }

        navigation<DestinationScreens.DirectMigrationRoot>(
            startDestination = DestinationScreens.DirectMigrationSelectSourceApp
        ) {

            val totalSteps = DirectMigrationRoutes.getRouteSize()

            composable<DestinationScreens.DirectMigrationSelectSourceApp> { navBackStackEntry ->
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
                        pluvNavController.navigate(DestinationScreens.DirectMigrationSelectDestinationApp)
                    }
                )
            }

            composable<DestinationScreens.UploadPlaylistScreenShot> { navBackStackEntry ->
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
                        pluvNavController.navigate(DestinationScreens.DirectMigrationSelectDestinationApp)
                    }
                )
            }

            composable<DestinationScreens.DirectMigrationSelectDestinationApp> { navBackStackEntry ->
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
                        pluvNavController.navigate(DestinationScreens.ExecuteDirectMigration)
                    },
                )
            }

            composable<DestinationScreens.ExecuteDirectMigration> { navBackStackEntry ->
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
                    showSnackBar = showSnackBar,
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    navigateToSelectDestinationApp = {
                        pluvNavController.popBackStack()
                    },
                    navigateToSelectPlaylist = {
                        pluvNavController.navigate(DestinationScreens.SelectMigratePlaylist)
                    },
                    navigateToSelectMigrationMusic = {
                        pluvNavController.navigate(DestinationScreens.SelectMigrationMusic)
                    }
                )
            }

            composable<DestinationScreens.SelectMigratePlaylist> { navBackStackEntry ->
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
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    navigateToSelectMigrationMusic = {
                        pluvNavController.navigate(DestinationScreens.SelectMigrationMusic)
                    }
                )
            }

            composable<DestinationScreens.SelectMigrationMusic> { navBackStackEntry ->
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
//                navController.navigate(DestinationScreens.SelectMigratePlaylist.route)
                    },
                    navigateToSelectSimilarMusic = {
                        pluvNavController.navigate(DestinationScreens.SelectSimilarMusic)
                    },
                    navigateToShowNotFoundMusic = {
                        pluvNavController.navigate(DestinationScreens.ShowNotFoundMusic)
                    },
                    onShowSnackBar = showSnackBar,
                    navigateToMigrationProcess = {
                        val navOptions = NavOptions.Builder().setPopUpTo(
                            pluvNavController.navController.graph.findStartDestination().id,
                            false
                        ).build()
                        pluvNavController.navigate(DestinationScreens.MigrationProcess, navOptions)
                    },
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    )
                )
            }

            composable<DestinationScreens.SelectSimilarMusic> { navBackStackEntry ->
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
                        pluvNavController.navigate(DestinationScreens.ShowNotFoundMusic)
                    },
                    navigateToMigrationProcess = {
                        val navOptions = NavOptions.Builder().setPopUpTo(
                            pluvNavController.navController.graph.findStartDestination().id,
                            false
                        ).build()
                        pluvNavController.navigate(DestinationScreens.MigrationProcess, navOptions)
                    },
                )
            }

            composable<DestinationScreens.ShowNotFoundMusic> { navBackStackEntry ->
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
                        pluvNavController.navigate(DestinationScreens.MigrationProcess, navOptions)
                    },
                )
            }

            composable<DestinationScreens.MigrationProcess> { navBackStackEntry ->
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
                        pluvNavController.navigate(DestinationScreens.MigratedResult, navOptions)
                    }
                )
            }

            composable<DestinationScreens.MigratedResult> { navBackStackEntry ->
                MigratedResultScreen(
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
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
            DestinationScreens.DirectMigrationSelectSourceApp,
            DestinationScreens.UploadPlaylistScreenShot
        ),
        DestinationScreens.DirectMigrationSelectDestinationApp,
        DestinationScreens.ExecuteDirectMigration,
        DestinationScreens.SelectMigratePlaylist,
        DestinationScreens.SelectMigrationMusic,
        DestinationScreens.SelectSimilarMusic,
        DestinationScreens.ShowNotFoundMusic
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

