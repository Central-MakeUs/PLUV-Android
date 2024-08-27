package com.cmc15th.pluv.ui.home.migrate.common.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.core.designsystem.component.PLUVButton
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Gray300
import com.cmc15th.pluv.core.designsystem.theme.Gray800

@Composable
fun PreviousOrMigrateButton(
    modifier: Modifier = Modifier,
    isPreviousButtonEnabled: Boolean = true,
    isNextButtonEnabled: Boolean = false,
    onPreviousClick: () -> Unit,
    onMigrateClick: () -> Unit
) {
    Column {
        Divider(color = Color(0xFFF2F2F2), thickness = 1.dp)
        Spacer(modifier = Modifier.size(10.dp))

        Row(
            modifier = modifier
        ) {
            PLUVButton(
                onClick = { onPreviousClick() },
                containerColor = if (isPreviousButtonEnabled) Color.White else Color(0xFFFFFFFF),
                contentColor = if (isPreviousButtonEnabled) Color.Black else Color(0xFFDEDEDE),
                modifier = Modifier
                    .weight(0.3f)
                    .height(58.dp)
                    .border(width = 1.dp, color = Color(0xFFF2F2F2), shape = RoundedCornerShape(8.dp)),
                enabled = isPreviousButtonEnabled,
                content = {
                    PreviousText()
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            PLUVButton(
                onClick = { onMigrateClick() },
                containerColor = if (isNextButtonEnabled) Gray800 else Gray300,
                contentColor = if (isNextButtonEnabled) Color.White else Gray800,
                modifier = Modifier
                    .weight(0.7f)
                    .height(58.dp),
                enabled = isNextButtonEnabled,
                content = {
                    MigrateText()
                }
            )
        }
    }

}

@Composable
fun PreviousText() {
    Text(text = "이전", style = Content1)
}

@Composable
fun MigrateText() {
    Text(text = "옮기기", style = Content1)
}

@Composable
@Preview
fun PreviousOrMigrateButtonPreview() {
    PreviousOrMigrateButton(
        onPreviousClick = {},
        onMigrateClick = {}
    )
}