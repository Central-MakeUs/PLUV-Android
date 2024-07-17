package com.cmc15th.pluv.core.designsystem.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.R

@Composable
fun PlaylistCheckBox(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
) {
    val checkImageRes = when (isChecked) {
        true -> R.drawable.checked_button
        false -> R.drawable.unchecked_button
    }

    Icon(
        painter = painterResource(id = checkImageRes),
        contentDescription = null,
        modifier = modifier.size(28.dp),
        tint = Color.Unspecified
    )
}