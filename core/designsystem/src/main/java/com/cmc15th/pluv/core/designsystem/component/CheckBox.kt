package com.cmc15th.pluv.core.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.cmc15th.pluv.core.designsystem.R

@Composable
fun PlaylistCheckBox(
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(id = R.drawable.check_button),
        contentDescription = null,
        modifier = modifier,
        tint = Color.Unspecified
    )
}