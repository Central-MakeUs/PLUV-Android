package com.cmc15th.pluv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
import com.cmc15th.pluv.ui.home.migrate.direct.SelectSourceAppScreen

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
                }
            )
        }

        navigation(
            startDestination = DestinationScreens.SelectApp.route,
            route = DestinationScreens.DirectMigrationRoot.route
            route = DestinationScreens.DirectMigrationRoot.route,
            startDestination = DestinationScreens.DirectMigrationSelectSourceApp.route
        ) {

            composable(route = DestinationScreens.DirectMigrationSelectSourceApp.route) {
                Column {
                    TopBarWithProgress(navController = navController)
                    Spacer(modifier = Modifier.height(28.dp))
                    SelectSourceAppScreen(
                        viewModel = navController.sharedViewModel(route = DestinationScreens.DirectMigrationRoot.route),
                        navigateToSelectDestinationApp = {
                            navController.navigate(DestinationScreens.DirectMigrationSelectDestinationApp.route)
                        }
                    )
                }
            }

            composable(route = DestinationScreens.DirectMigrationSelectDestinationApp.route) {
                Column {
                    TopBarWithProgress(navController = navController)
                    Spacer(modifier = Modifier.height(28.dp))
                    SelectDestinationAppScreen(
                        viewModel = navController.sharedViewModel(route = DestinationScreens.DirectMigrationRoot.route),
                        navigateToSelectSource = {
                            navController.navigate(DestinationScreens.DirectMigrationSelectSourceApp.route) {
                                popUpTo(DestinationScreens.DirectMigrationSelectSourceApp.route) { inclusive = true }
                            }
                        },
                        navigateToDisplayMigrationPath = {
                            navController.navigate(DestinationScreens.ExecuteDirectMigration.route)
                        }
                    )
                }
            }

            composable(route = DestinationScreens.ExecuteDirectMigration.route) {
                Column {
                    TopBarWithProgress(navController = navController)
                    Spacer(modifier = Modifier.height(28.dp))
                    DisplayMigrationPathScreen(
                        viewModel = navController.sharedViewModel(route = DestinationScreens.DirectMigrationRoot.route),
                        navigateToSelectDestinationApp = {
                            navController.navigate(DestinationScreens.DirectMigrationSelectDestinationApp.route) {
                                popUpTo(DestinationScreens.DirectMigrationSelectDestinationApp.route) { inclusive = true }
                            }
                        },
                        navigateToSelectPlaylist = {
                            navController.navigate(DestinationScreens.SelectMigratePlaylist.route)
                        }
                    )
                }
            }

            composable(route = DestinationScreens.SelectMigratePlaylist.route) {
                Column {
                    TopBarWithProgress(navController = navController)
                    Spacer(modifier = Modifier.height(28.dp))
                    SelectMigratePlaylistScreen(
                        navigateToDisplayMigrationPath = {
                            navController.navigate(DestinationScreens.ExecuteDirectMigration.route) {
                                popUpTo(DestinationScreens.ExecuteDirectMigration.route) { inclusive = true }
                            }
                        },
                        viewModel = navController.sharedViewModel(route = DestinationScreens.DirectMigrationRoot.route)
                    )
                }
            }
        }
    }
}

@Composable
fun TopBarWithProgress(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val parentRoute = navBackStackEntry?.destination?.parent?.route
    val totalSteps = 5
    val currentStep = when (parentRoute) {
        DestinationScreens.SelectApp.route -> 1
        DestinationScreens.SelectMigratePlyListRoot.route -> 2
        DestinationScreens.ExecuteDirectMigration.route -> 3
        else -> 0
    }
    val progress = currentStep.toFloat() / totalSteps

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 8.dp, end = 24.dp, top = 11.dp, bottom = 11.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.leftarrow),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
            Row {
                Text(
                    text = "$currentStep",
                    color = colorResource(id = R.color.current_step_color)
                )
                Text(
                    text = "/$totalSteps",
                    color = Color.Black
                )
            }
        }
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = colorResource(id = R.color.current_step_color),
            trackColor = Color.LightGray,
        )
    }
}

@Composable
inline fun <reified T : ViewModel> NavController.sharedViewModel(route: String): T {
    val parentEntry = remember {
        getBackStackEntry(route)
    }
    return hiltViewModel(parentEntry)
}

