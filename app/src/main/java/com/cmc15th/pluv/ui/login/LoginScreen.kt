package com.cmc15th.pluv.ui.login

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.component.PLUVButton
import com.cmc15th.pluv.core.designsystem.theme.Title4
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

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 107.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Icon(
                modifier = Modifier
                    .width(102.dp)
                    .height(38.dp),
                painter = painterResource(id = R.drawable.pluvlogo),
                contentDescription = "Pluv Logo"
            )

            Spacer(modifier = Modifier.size(27.dp))

            Text(
                text = stringResource(id = R.string.login_welcome),
                style = Title4,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 24.dp, end = 24.dp, bottom = 119.dp),
        ) {
            Column {
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
        content = {
            Icon(
                painter = painterResource(id = R.drawable.googlelogin),
                contentDescription = "Google Login",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = stringResource(id = R.string.google_login), style = Title5)
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
        content = {
            Icon(
                painter = painterResource(id = R.drawable.spotifylogin),
                contentDescription = "Spotify Login",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = stringResource(id = R.string.spotify_login), style = Title5)
        }
    )
}


@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen()
}



