package com.cmc15th.pluv

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.cmc15th.pluv.core.designsystem.theme.PLUVTheme
import com.cmc15th.pluv.navigation.rememberPLUVNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        var keepSplashScreen = true
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                keepSplashScreen
            }
        }
        super.onCreate(savedInstanceState)

        setContent {
            val systemUiController = rememberSystemUiController()
            DisposableEffect(systemUiController) {
                systemUiController.setSystemBarsColor(
                    color = Color.White,
                    darkIcons = true,
                    isNavigationBarContrastEnforced = false
                )
                onDispose {}
            }

            val navController = rememberPLUVNavController(navController = rememberNavController())
            val loginState by viewModel.loginState.collectAsStateWithLifecycle()

            PLUVTheme {
                loginState?.let { state ->
                    Log.d("MainActivityViewModel", "onCreate: isLoggedIn: $state")
                    keepSplashScreen = false
                    MainScreen(
                        pluvNavController = navController,
                        loginState = state
                    )
                }
            }
        }
    }
}

