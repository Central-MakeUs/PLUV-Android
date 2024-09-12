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
import com.cmc15th.pluv.feature.feed.FeedDetailScreen
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
import com.cmc15th.pluv.navigation.DestinationScreens.MigrationRoute

@Composable
internal fun PLUVNavHost(
    pluvNavController: PLUVNavController,
    loginState: Boolean,
    showSnackBar: (String) -> Unit = {}
) {

    NavHost(
        navController = pluvNavController.navController,
        startDestination = if (loginState) {
            BottomTabRoute.Home
        } else {
            DestinationScreens.Onboarding
        }
    ) {
//        composable<DestinationScreens.Splash> { navBackStackEntry ->
//            val navOptions = NavOptions.Builder().setPopUpTo(
//                pluvNavController.navController.graph.findStartDestination().id,
//                inclusive = true
//            ).build()
//
//            SplashScreen(
//                viewModel = hiltViewModel(navBackStackEntry),
//                navigateToOnboarding = {
//                    pluvNavController.navigate(DestinationScreens.Onboarding, navOptions)
//                },
//                navigateToHome = {
//                    pluvNavController.navigate(BottomTabRoute.Home, navOptions)
//                }
//            )
//        }

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
                showSnackBar = showSnackBar,
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
                    pluvNavController.navigate(MigrationRoute)
                },
                navigateToScreenShotMigration = {
                    pluvNavController.navigate(MigrationRoute.UploadPlaylistScreenShot)
                },
                navigateToFeedDetail = { feedId ->
                    pluvNavController.navigate(DestinationScreens.FeedInfo(feedId))
                },
                navigateToHistoryDetail = { historyId ->
                    pluvNavController.navigate(DestinationScreens.HistoryDetail(historyId))
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
            FeedDetailScreen(
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

        navigation<MigrationRoute>(
            startDestination = MigrationRoute.DirectMigrationSelectSourceApp
        ) {

            val totalSteps = MigrationRoute.routes.size

            composable<MigrationRoute.DirectMigrationSelectSourceApp> { navBackStackEntry ->
                val currentRoute = navBackStackEntry.destination.route
                SelectSourceAppScreen(
                    currentStep = MigrationRoute.currentStep(pluvNavController.getMigrationDestination()),
                    totalStep = totalSteps,
                    onCloseClick = {
                        pluvNavController.clearBackStackToRoot()
                    },
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    navigateToSelectDestinationApp = {
                        pluvNavController.navigate(MigrationRoute.DirectMigrationSelectDestinationApp)
                    }
                )
            }

            composable<MigrationRoute.UploadPlaylistScreenShot> { navBackStackEntry ->
                UploadPlaylistScreenShotScreen(
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry
                    ),
                    totalStep = totalSteps,
                    currentStep = MigrationRoute.currentStep(pluvNavController.getMigrationDestination()),
                    onCloseClick = {
                        pluvNavController.clearBackStackToRoot()
                    },
                    navigateToSelectDestinationApp = {
                        pluvNavController.navigate(MigrationRoute.DirectMigrationSelectDestinationApp)
                    }
                )
            }

            composable<MigrationRoute.DirectMigrationSelectDestinationApp> { navBackStackEntry ->
                SelectDestinationAppScreen(
                    currentStep = MigrationRoute.currentStep(pluvNavController.getMigrationDestination()),
                    totalStep = totalSteps,
                    onCloseClick = {
                        pluvNavController.clearBackStackToRoot()
                    },
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    navigateToSelectSource = {
                        pluvNavController.popBackStack()
                    },
                    navigateToDisplayMigrationPath = {
                        pluvNavController.navigate(MigrationRoute.ExecuteDirectMigration)
                    },
                )
            }

            composable<MigrationRoute.ExecuteDirectMigration> { navBackStackEntry ->
                DisplayMigrationPathScreen(
                    currentStep = MigrationRoute.currentStep(pluvNavController.getMigrationDestination()),
                    totalStep = totalSteps,
                    onCloseClick = {
                        pluvNavController.clearBackStackToRoot()
                    },
                    showSnackBar = showSnackBar,
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    navigateToSelectDestinationApp = {
                        pluvNavController.popBackStack()
                    },
                    navigateToSelectPlaylist = {
                        pluvNavController.navigate(MigrationRoute.SelectMigratePlaylist)
                    },
                    navigateToSelectMigrationMusic = {
                        pluvNavController.navigate(MigrationRoute.SelectMigrationMusic)
                    }
                )
            }

            composable<MigrationRoute.SelectMigratePlaylist> { navBackStackEntry ->
                SelectMigratePlaylistScreen(
                    currentStep = MigrationRoute.currentStep(pluvNavController.getMigrationDestination()),
                    totalStep = totalSteps,
                    onCloseClick = {
                        pluvNavController.clearBackStackToRoot()
                    },
                    navigateToDisplayMigrationPath = {
                        pluvNavController.popBackStack()
                    },
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    navigateToSelectMigrationMusic = {
                        pluvNavController.navigate(MigrationRoute.SelectMigrationMusic)
                    }
                )
            }

            composable<MigrationRoute.SelectMigrationMusic> { navBackStackEntry ->
                SelectMigrationMusicScreen(
                    currentStep = MigrationRoute.currentStep(pluvNavController.getMigrationDestination()),
                    totalStep = totalSteps,
                    onCloseClick = {
                        pluvNavController.clearBackStackToRoot()
                    },
                    navigateToSelectPlaylist = {
                        pluvNavController.popBackStack()
                    },
                    navigateToSelectSimilarMusic = {
                        pluvNavController.navigate(MigrationRoute.SelectSimilarMusic)
                    },
                    navigateToShowNotFoundMusic = {
                        pluvNavController.navigate(MigrationRoute.ShowNotFoundMusic)
                    },
                    onShowSnackBar = showSnackBar,
                    navigateToMigrationProcess = {
                        val navOptions = NavOptions.Builder().setPopUpTo(
                            pluvNavController.navController.graph.findStartDestination().id,
                            false
                        ).build()
                        pluvNavController.navigate(MigrationRoute.MigrationProcess, navOptions)
                    },
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    )
                )
            }

            composable<MigrationRoute.SelectSimilarMusic> { navBackStackEntry ->
                SelectSimilarMusicScreen(
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    currentStep = MigrationRoute.currentStep(pluvNavController.getMigrationDestination()),
                    totalStep = totalSteps,
                    onCloseClick = {
                        pluvNavController.clearBackStackToRoot()
                    },
                    onShowSnackBar = showSnackBar,
                    navigateToSelectMigrationMusic = {
                        pluvNavController.popBackStack()
                    },
                    navigateToShowNotFoundMusic = {
                        pluvNavController.navigate(MigrationRoute.ShowNotFoundMusic)
                    },
                    navigateToMigrationProcess = {
                        val navOptions = NavOptions.Builder().setPopUpTo(
                            pluvNavController.navController.graph.findStartDestination().id,
                            false
                        ).build()
                        pluvNavController.navigate(MigrationRoute.MigrationProcess, navOptions)
                    },
                )
            }

            composable<MigrationRoute.ShowNotFoundMusic> { navBackStackEntry ->
                ShowNotFoundMusicScreen(
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    onShowSnackBar = showSnackBar,
                    onCloseClick = {
                        pluvNavController.clearBackStackToRoot()
                    },
                    navigateToMigrationProcess = {
                        val navOptions = NavOptions.Builder().setPopUpTo(
                            pluvNavController.navController.graph.findStartDestination().id,
                            false
                        ).build()
                        pluvNavController.navigate(MigrationRoute.MigrationProcess, navOptions)
                    },
                )
            }

            composable<MigrationRoute.MigrationProcess> { navBackStackEntry ->
                val navOptions = NavOptions.Builder().setPopUpTo(
                    pluvNavController.navController.graph.findStartDestination().id,
                    false
                ).build()
                MigrationProcessScreen(
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    showSnackBar = showSnackBar,
                    navigateToHome = {
                        pluvNavController.clearBackStackToRoot()
                    },
                    onCloseClick = {
                        pluvNavController.clearBackStackToRoot()
                    },
                    onStopMigrationClicked = {
                        pluvNavController.clearBackStackToRoot()
                    },
                    navigateToMigrationResult = {
                        pluvNavController.navigate(MigrationRoute.MigratedResult, navOptions)
                    }
                )
            }

            composable<MigrationRoute.MigratedResult> { navBackStackEntry ->
                MigratedResultScreen(
                    viewModel = pluvNavController.sharedViewModel<DirectMigrationViewModel>(
                        navBackStackEntry = navBackStackEntry,
                    ),
                    showSnackBar = showSnackBar,
                    navigateToHome = {
                        pluvNavController.clearBackStackToRoot()
                    }
                )
            }
        }
    }
}

