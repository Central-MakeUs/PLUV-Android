package com.cmc15th.pluv.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.cmc15th.pluv.R

@Composable
fun PlaylistCheckBox(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit
) {
    val checkImageRes = when (isChecked) {
        true -> R.drawable.checked_button
        false -> R.drawable.unchecked_button
    }

    Icon(
        painter = painterResource(id = checkImageRes),
        contentDescription = null,
        modifier = modifier.clickable { onCheckedChange(!isChecked) }
    )
}