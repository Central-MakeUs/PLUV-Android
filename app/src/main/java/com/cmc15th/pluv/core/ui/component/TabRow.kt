package com.cmc15th.pluv.core.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.core.designsystem.theme.Gray400
import com.cmc15th.pluv.core.designsystem.theme.PrimaryDefault
import com.cmc15th.pluv.core.designsystem.theme.Title5

@Composable
fun TransferredMusicTabRow(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = PrimaryDefault
            )
        }
    ) {
        tabNames.forEachIndexed { index, name ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    onTabSelected(index)
                }
            ) {
                Text(
                    text = name,
                    style = Title5,
                    color = if (selectedTabIndex == index) PrimaryDefault else Gray400,
                    modifier = Modifier.padding(vertical = 11.5.dp)
                )
            }
        }
    }
}

private val tabNames = listOf("옮긴 곡", "안 옮긴 곡")