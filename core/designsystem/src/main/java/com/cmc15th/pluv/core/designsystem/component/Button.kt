package com.cmc15th.pluv.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.GoogleLogin

@Composable
fun PLUVButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    containerColor: Color,
    contentColor: Color,
    contentPadding: PaddingValues = PaddingValues(vertical = 19.dp),
    elevation: ButtonElevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 0.dp),
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
    ) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = contentPadding,
        elevation = elevation,
        content = content,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun GoogleLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    description: String = ""
) {
    PLUVButton(
        modifier = modifier,
        onClick = { onClick() },
        containerColor = Color.White,
        contentColor = Color.Black,
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
                    text = description,
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
    description: String = ""
) {
    PLUVButton(
        modifier = modifier,
        onClick = { onClick() },
        containerColor = Color(0xFF1ED760),
        contentColor = Color.Black,
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
                Text(text = description, style = Content2, modifier = Modifier.align(Alignment.Center))
            }
        }
    )
}