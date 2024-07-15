package com.cmc15th.pluv.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MigrateButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    containerColor: Color,
    contentColor: Color,
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
        content = content,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    )
}