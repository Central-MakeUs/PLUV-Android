package com.cmc15th.pluv

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions

/**
 * Main NavController
 */

@Composable
fun rememberPLUVNavController(
    navController: NavHostController = rememberNavController()
): PLUVNavController = remember {
    PLUVNavController(navController)
}

class PLUVNavController(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val currentBottomTab: BottomTab?
        @Composable
        get() = BottomTab.entries.find { it.route == currentDestination?.route }

    val bottomTabs: List<BottomTab> = BottomTab.entries

    @Composable
    fun isVisibleBottomBar(): Boolean {
        //Fixme
        return currentDestination?.route in BottomTab.entries.map { it.route } || currentDestination?.route == DestinationScreens.Feed.route
    }

    fun navigate(route: String, navOptions: NavOptions? = null) {
        navController.navigate(route, navOptions)
    }

    fun navigateToLogin() {
        navController.navigate(DestinationScreens.Login.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToHome() {
        navController.navigate(BottomTab.HOME.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToOnboarding() {
        navController.navigate(DestinationScreens.Onboarding.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigateToBottomTab(tab: BottomTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        navigate(tab.route, navOptions)
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
}