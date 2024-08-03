package com.cmc15th.pluv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cmc15th.pluv.core.designsystem.theme.Gray300
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.Title6

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            PLUVBottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(horizontal = 33.dp),
                isVisible = navController.isVisibleBottomBar(),
                bottomTabs = BottomTab.entries,
                currentTab = navController.currentDestination?.route.orEmpty(),
                onSelected = { route ->
                    navController.navigate(route)
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PLUVNavHost(
                navController = navController
            )
        }
    }
}

@Composable
fun PLUVBottomBar(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    bottomTabs: List<BottomTab>,
    currentTab: String,
    onSelected: (String) -> Unit
) {
    if (isVisible) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            bottomTabs.forEach { tab ->
                BottomBarItem(
                    icon = tab.unselectedIconId,
                    selectedIcon = tab.selectedIconId,
                    label = tab.iconTextId,
                    route = tab.route,
                    isSelected = tab.route == currentTab,
                    onSelected = {
                        onSelected(tab.route)
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBarItem(
    icon: Int,
    selectedIcon: Int,
    label: Int,
    route: String,
    isSelected: Boolean,
    onSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(90.dp)
            .selectable(
                selected = isSelected,
                onClick = { onSelected(route) }
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            painter = if (isSelected) painterResource(id = selectedIcon) else painterResource(id = icon),
            contentDescription = "Botombar Item",
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(id = label),
            style = Title6,
            color = if (isSelected) Gray800 else Gray300
        )
    }
}
