package com.cmc15th.pluv.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.feature.splash.viewmodel.SplashUiEffect
import com.cmc15th.pluv.feature.splash.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToOnboarding: () -> Unit = {},
    navigateToHome: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is SplashUiEffect.IsLoggedIn -> {
                    if (effect.state) {
                        navigateToHome()
                    } else {
                        navigateToOnboarding()
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}