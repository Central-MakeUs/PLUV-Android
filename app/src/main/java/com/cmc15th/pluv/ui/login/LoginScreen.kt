package com.cmc15th.pluv.ui.login

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.component.LoadingIndicator
import com.cmc15th.pluv.core.designsystem.component.PLUVButton
import com.cmc15th.pluv.core.designsystem.theme.Content0
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.GoogleLogin
import com.cmc15th.pluv.core.designsystem.theme.Title5
import com.cmc15th.pluv.ui.common.contract.GoogleApiContract
import com.cmc15th.pluv.ui.common.contract.SpotifyAuthContract
import com.cmc15th.pluv.ui.login.viewmodel.LoginUiEffect
import com.cmc15th.pluv.ui.login.viewmodel.LoginUiEvent
import com.cmc15th.pluv.ui.login.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToHome: () -> Unit = {}
) {
    val googleLoginResultLauncher = rememberLauncherForActivityResult(
        contract = GoogleApiContract()
    ) { task ->
        viewModel.setEvent(LoginUiEvent.GoogleLogin(task))
    }

    val spotifyLoginResultLauncher = rememberLauncherForActivityResult(
        contract = SpotifyAuthContract()
    ) { task ->
        viewModel.setEvent(LoginUiEvent.SpotifyLogin(task))
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is LoginUiEffect.OnLoginSuccess -> {
                    Log.d("LoginScreen", "LoginScreen: LoginSuccess")
                    navigateToHome()
                }
                is LoginUiEffect.OnLoginFailure -> {
                    Log.d("LoginScreen", "LoginScreen: LoginF")

                    //TODO show error message
                }
            }
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 136.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Icon(
                modifier = Modifier
                    .width(109.dp)
                    .height(39.dp),
                painter = painterResource(id = R.drawable.pluvlogo),
                contentDescription = "Pluv Logo",
                tint = Color.Unspecified
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 24.dp, end = 24.dp, bottom = 103.dp),
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.login_welcome),
                    style = Content0,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(36.dp))
                LoginButtons(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    onGoogleLoginClick = {
                        googleLoginResultLauncher.launch(1)
                    },
                    onSpotifyLoginClick = {
                        spotifyLoginResultLauncher.launch(1)
                    }
                )

                Spacer(modifier = Modifier.size(31.dp))

                Text(
                    text = "회원가입하면 서비스 이용 약관 및\n개인정보 이용 약관에 동의하게 됩니다.",
                    style = Title5,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun LoginButtons(
    modifier: Modifier = Modifier,
    onGoogleLoginClick: () -> Unit = {},
    onSpotifyLoginClick: () -> Unit = {},
    onAppleLoginClick: () -> Unit = {},
) {
    Column {
        GoogleLoginButton(
            modifier = modifier.border(
                1.dp, Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp)
            ),
            onClick = { onGoogleLoginClick() }
        )

        Spacer(modifier = Modifier.size(14.dp))

        SpotifyLoginButton(
            modifier = modifier,
            onClick = { onSpotifyLoginClick() }
        )

        Spacer(modifier = Modifier.size(14.dp))
    }
}

@Composable
fun GoogleLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    PLUVButton(
        modifier = modifier,
        onClick = { onClick() },
        containerColor = Color.White,
        contentColor = Color.Black,
        contentPadding = PaddingValues(0.dp),
        content = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.googlelogin),
                    contentDescription = "Google Login",
                    modifier = Modifier
                        .padding(start = 24.dp)
                        .size(18.dp)
                        .align(Alignment.CenterStart),
                    tint = Color.Unspecified
                )
                Text(
                    text = stringResource(id = R.string.google_login),
                    style = GoogleLogin,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    )
}

@Composable
fun SpotifyLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    PLUVButton(
        modifier = modifier,
        onClick = { onClick() },
        containerColor = Color(0xFF1ED760),
        contentColor = Color.Black,
        contentPadding = PaddingValues(0.dp),
        content = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.spotifylogin),
                    contentDescription = "Spotify Login",
                    modifier = Modifier
                        .padding(start = 24.dp)
                        .size(18.dp)
                        .align(Alignment.CenterStart),
                    tint = Color.Unspecified
                )
                Text(text = stringResource(id = R.string.spotify_login), style = Content2, modifier = Modifier.align(Alignment.Center))
            }
        }
    )
}


@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen()
}



