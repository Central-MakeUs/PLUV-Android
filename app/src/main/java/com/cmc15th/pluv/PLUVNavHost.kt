package com.cmc15th.pluv

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

@Composable
inline fun <reified T : ViewModel> NavController.sharedViewModel(route: String): T {
    val parentEntry = remember {
        getBackStackEntry(route)
    }
    return hiltViewModel(parentEntry)
}

