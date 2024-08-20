package com.cmc15th.pluv.core.designsystem.component

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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.cmc15th.pluv.R
import com.cmc15th.pluv.ui.home.migrate.common.component.FetchPlaylistLoadingIcon

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
                        modifier = Modifier.size(100.dp).padding(10.dp), // Control the size of the icon box
                        contentAlignment = Alignment.Center
                    ) {
                        icon()
                    }
                    Spacer(modifier = Modifier.size(37.dp))
                    Text(
                        text = description,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(25.dp))
                    if (progressVisible) {
                        LinearProgressIndicator(
                            color = colorResource(id = R.color.current_step_color),
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
@Preview
fun LoadingDialogPreview() {
    LoadingDialog(
        icon = {
            FetchPlaylistLoadingIcon()
        },
        description = "플레이리스트를\n불러오는 중이예요!",
        onDismissRequest = {}
    )
}

