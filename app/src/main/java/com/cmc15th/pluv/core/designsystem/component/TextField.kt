package com.cmc15th.pluv.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Content2

@Composable
fun PLUVTextField(
    modifier: Modifier = Modifier,
    maxLength: Int = 10,
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp, bottom = 13.dp, start = 12.dp)
                .align(Alignment.CenterStart),
            value = value,
            onValueChange = onValueChange,
            textStyle = Content1,
        )

        Text(
            text = "${value.length}/$maxLength",
            style = Content2,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        )
    }
}