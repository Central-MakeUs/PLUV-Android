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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.core.designsystem.component.PLUVSnackBar
import com.cmc15th.pluv.core.designsystem.theme.Gray300
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.Title6
import com.cmc15th.pluv.navigation.BottomTab
import com.cmc15th.pluv.navigation.PLUVNavController
import com.cmc15th.pluv.navigation.PLUVNavHost
import com.cmc15th.pluv.navigation.rememberPLUVNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SnackBarDuration = 1500L

@Composable
fun MainScreen(
    pluvNavController: PLUVNavController = rememberPLUVNavController()
) {
    val snackBarState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val showSnackBar: (String) -> Unit = { message ->
        coroutineScope.launch {
            val job = launch {
                snackBarState.showSnackbar(message)
            }
            delay(SnackBarDuration)
            job.cancel()
        }
    }

    Scaffold(
        bottomBar = {
            PLUVBottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(horizontal = 33.dp),
                isVisible = pluvNavController.isVisibleBottomBar(),
                bottomTabs = pluvNavController.bottomTabs,
                currentTab = pluvNavController.currentBottomTab?.route.orEmpty(),
                onSelected = { tab ->
                    pluvNavController.navigateToBottomTab(tab)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarState,
                snackbar = { data ->
                    PLUVSnackBar(
                        content = data.visuals.message,
                    )
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
                pluvNavController = pluvNavController,
                showSnackBar = { message ->
                    showSnackBar(message)
                }
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
    onSelected: (BottomTab) -> Unit
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
                    isSelected = tab.route == currentTab,
                    onSelected = {
                        onSelected(tab)
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
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(90.dp)
            .selectable(
                selected = isSelected,
                onClick = { onSelected() }
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
