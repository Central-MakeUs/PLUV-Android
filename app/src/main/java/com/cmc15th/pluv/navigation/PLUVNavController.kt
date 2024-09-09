package com.cmc15th.pluv.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions

/**
 * Main NavController
 */

@Composable
internal fun rememberPLUVNavController(
    navController: NavHostController = rememberNavController()
): PLUVNavController = remember {
    PLUVNavController(navController)
}

internal class PLUVNavController(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val currentBottomTab: BottomTab?
        @Composable
        get() = BottomTab.entries.find { bottomTab ->
            currentDestination?.hierarchy?.any { it.hasRoute(route = bottomTab.route::class) } == true
        }

    val bottomTabs: List<BottomTab> = BottomTab.entries

    @Composable
    fun isVisibleBottomBar(): Boolean {
        return currentDestination?.hierarchy?.any { destination ->
            BottomTab.entries.any { bottomTab -> destination.hasRoute(bottomTab.route::class) }
        } ?: false
    }

    fun navigate(route: DestinationScreens, navOptions: NavOptions? = null) {
        if (navOptions == null) {
            navController.navigate(route) {
                launchSingleTop = true
            }
        } else {
            navController.navigate(route, navOptions)
        }
    }

    fun navigateToLogin() {
        navController.navigate(DestinationScreens.Login) {
            popUpTo(navController.graph.id) {
                inclusive = false
            }
            launchSingleTop = true
        }
    }

    fun popBackStack() {
        // popBackStack 사
        navController.popBackStack()
    }

    fun navigateToBottomTab(tab: BottomTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (tab) {
            BottomTab.HOME -> navController.navigate(BottomTabRoute.Home, navOptions)
            BottomTab.FEED -> navController.navigate(BottomTabRoute.Feed, navOptions)
            BottomTab.MY_PAGE -> navController.navigate(DestinationScreens.Mypage, navOptions)
        }
    }

    @Composable
    inline fun <reified T : ViewModel> sharedViewModel(
        navBackStackEntry: NavBackStackEntry
    ): T {
        val navGraphRoute = navBackStackEntry.destination.parent?.route ?: return hiltViewModel()

        val parentEntry = remember(navBackStackEntry) {
            navController.getBackStackEntry(navGraphRoute)
        }

        return hiltViewModel(parentEntry)
    }

    companion object {
        private const val TAG = "PLUVNavController"
    }
}