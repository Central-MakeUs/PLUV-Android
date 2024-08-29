package com.cmc15th.pluv.core.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.core.designsystem.theme.SubContent1

@Composable
fun PLUVSnackBar(
    content: String,
    isError: Boolean = false,
) {

    Snackbar(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 30.dp),
        containerColor = Color(0xFF1E1E1E).copy(alpha = 0.9f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row {
//            Icon(
//                painter =
//                if (isError) painterResource(id = R.drawable.snackerror)
//                else painterResource(id = R.drawable.snacksuccess),
//                tint = Color.Unspecified,
//                contentDescription = "snack icon",
//                modifier = Modifier.size(22.dp)
//            )
//            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = content,
                style = SubContent1,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PLUVSnackBarPreview() {
    PLUVSnackBar(
        content = "Content"
    )
}