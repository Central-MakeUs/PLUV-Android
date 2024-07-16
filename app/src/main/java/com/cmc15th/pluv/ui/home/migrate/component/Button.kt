package com.cmc15th.pluv.ui.home.migrate.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.core.designsystem.component.PLUVButton
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Title4

@Composable
fun PreviousOrMigrateButton(
    modifier: Modifier = Modifier,
    isPreviousButtonEnabled: Boolean = true,
    isNextButtonEnabled: Boolean = false,
    onPreviousClick: () -> Unit,
    onMigrateClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        PLUVButton(
            onClick = { onPreviousClick() },
            containerColor = if (isPreviousButtonEnabled) Color.White else Color(0xFFFFFFFF),
            contentColor = if (isPreviousButtonEnabled) Color.Black else Color(0xFFDEDEDE),
            modifier = modifier.weight(0.33f),
            enabled = isPreviousButtonEnabled,
            content = {
                PreviousText()
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        PLUVButton(
            onClick = { onMigrateClick() },
            containerColor = if (isNextButtonEnabled) Color.Black else Color(0xFFDEDEDE),
            contentColor = if (isNextButtonEnabled) Color.White else Color(0xFFB0B0B0),
            modifier = modifier.weight(0.66f),
            enabled = isNextButtonEnabled,
            content = {
                MigrateText()
            }
        )
    }
}

@Composable
fun PreviousText() {
    Text(text = "이전", style = Content1)
}

@Composable
fun MigrateText() {
    Text(text = "옮기기", style = Title4)
}

@Composable
@Preview
fun PreviousOrMigrateButtonPreview() {
    PreviousOrMigrateButton(
        onPreviousClick = {},
        onMigrateClick = {}
    )
}