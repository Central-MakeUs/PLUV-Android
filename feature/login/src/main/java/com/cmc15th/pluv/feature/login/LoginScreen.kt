package com.cmc15th.pluv.feature.login

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.core.designsystem.component.GoogleLoginButton
import com.cmc15th.pluv.core.designsystem.component.LoadingIndicator
import com.cmc15th.pluv.core.designsystem.component.SpotifyLoginButton
import com.cmc15th.pluv.core.designsystem.theme.Content0
import com.cmc15th.pluv.core.designsystem.theme.Title5
import com.cmc15th.pluv.feature.common.contract.GoogleApiContract
import com.cmc15th.pluv.feature.common.contract.SpotifyAuthContract
import com.cmc15th.pluv.feature.login.viewmodel.LoginUiEffect
import com.cmc15th.pluv.feature.login.viewmodel.LoginUiEvent
import com.cmc15th.pluv.feature.login.viewmodel.LoginViewModel

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
                        .fillMaxWidth(),
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
            onClick = { onGoogleLoginClick() },
            description = stringResource(id = R.string.google_login)
        )

        Spacer(modifier = Modifier.size(14.dp))

        SpotifyLoginButton(
            modifier = modifier,
            onClick = { onSpotifyLoginClick() },
            description = stringResource(id = R.string.spotify_login)
        )

        Spacer(modifier = Modifier.size(14.dp))
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen()
}



