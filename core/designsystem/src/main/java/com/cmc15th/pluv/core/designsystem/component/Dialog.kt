package com.cmc15th.pluv.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Gray300
import com.cmc15th.pluv.core.designsystem.theme.Gray600
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.SemiTitle1
import com.cmc15th.pluv.core.designsystem.theme.TopAppBarProgress

@Composable
fun LoadingDialog(
    icon: @Composable () -> Unit,
    description: String,
    modifier: Modifier = Modifier,
    progressVisible: Boolean = true,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Box() {
//                Icon(
//                    painter = painterResource(id = R.drawable.dialogclose),
//                    contentDescription = null,
//                    tint = Color.Unspecified,
//                    modifier = Modifier
//                        .align(Alignment.TopEnd)
//                        .padding(18.dp)
//                        .size(24.dp)
//                        .clickable { onDismissRequest() }
//                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(60.dp))
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp), // Control the size of the icon box
                        contentAlignment = Alignment.Center
                    ) {
                        icon()
                    }
                    Spacer(modifier = Modifier.size(37.dp))
                    Text(
                        text = description,
                        style = SemiTitle1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(25.dp))
                    if (progressVisible) {
                        LinearProgressIndicator(
                            color = TopAppBarProgress,
                            trackColor = Color.LightGray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 51.dp)
                        )
                    }
                    Spacer(modifier = Modifier.padding(41.dp))
                }
            }
        }
    }
}

@Composable
fun ExitDialog(
    onDismissRequest: () -> Unit = {},
    onConfirmClicked: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.dialogclose),
                    contentDescription = null,
                    tint = Gray600,
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(end = 8.dp, top = 8.dp)
                        .size(24.dp)
                        .clickable { onDismissRequest() }
                        .align(Alignment.End)
                )
                Spacer(modifier = Modifier.size(18.dp))
                Text(
                    text = "옮기기를 중단할까요?",
                    style = SemiTitle1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(24.dp))
                Text(text = "지금 중단하면 진행 사항이 사라져요.", style = Content1, color = Color(0xFFF34242))
                Spacer(modifier = Modifier.size(36.dp))
                PLUVButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .border(1.dp, Gray300, RoundedCornerShape(8.dp)),
                    onClick = { onConfirmClicked() },
                    containerColor = Color.White,
                    contentColor = Gray800,
                    content = {
                        Text("확인", style = Content1)
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun LoadingDialogPreview() {
    LoadingDialog(
        icon = {
        },
        description = "플레이리스트를\n불러오는 중이예요!",
        onDismissRequest = {}
    )
}

@Composable
@Preview
fun ExitDialogPreview() {
    ExitDialog()
}

