package com.cmc15th.pluv.feature.login

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cmc15th.pluv.core.designsystem.component.PLUVButton
import com.cmc15th.pluv.core.designsystem.component.PLUVTextField
import com.cmc15th.pluv.feature.login.viewmodel.LoginUiEffect
import com.cmc15th.pluv.feature.login.viewmodel.LoginUiEvent
import com.cmc15th.pluv.feature.login.viewmodel.LoginViewModel

@Composable
fun TestLoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    showSnackBar: (String) -> Unit = {},
    navigateToHome: () -> Unit = {},
) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is LoginUiEffect.OnLoginSuccess -> {
                    Log.d("LoginScreen", "LoginScreen: LoginSuccess")
                    navigateToHome()
                }

                is LoginUiEffect.OnLoginFailure -> {
                    Log.d("LoginScreen", "LoginScreen: LoginF")
                    showSnackBar(effect.message)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TestLoginInputField(
            label = "Test Id",
            value = id,
            onValueChange = { id = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TestLoginInputField(
            label = "Test Password",
            value = password,
            onValueChange = { password = it })

        Spacer(modifier = Modifier.height(30.dp))

        PLUVButton(
            onClick = { viewModel.setEvent(LoginUiEvent.TestLogin(id, password)) },
            containerColor = Color.White,
            contentColor = Color.Black,
            modifier = Modifier.fillMaxWidth().padding(16.dp).border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
        ) {
            Text("Test Login")
        }
    }
}

@Composable
fun TestLoginInputField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(label)
        PLUVTextField(
            modifier = modifier.border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(4.dp)
            ),
            value = value,
            maxLength = 25,
            onValueChange = onValueChange
        )
    }
}

@Composable
@Preview
fun TestLoginScreenPreview() {
    TestLoginScreen()
}

