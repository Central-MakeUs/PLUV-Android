package com.cmc15th.pluv.ui.home.migrate.screenshot

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.R
import com.cmc15th.pluv.core.designsystem.component.TopBarWithProgress
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.cmc15th.pluv.ui.home.migrate.common.component.PreviousOrMigrateButton

@Composable
fun UploadPlaylistScreenShotScreen(
    modifier: Modifier = Modifier,
    currentStep: Int = 0,
    totalStep: Int = 0,
    onCloseClick: () -> Unit = {},
) {
    var showUploadHelpTextState by remember {
        mutableStateOf(true)
    }

    Scaffold(
        topBar = {
            TopBarWithProgress(
                totalStep = totalStep,
                currentStep = currentStep,
                onCloseClick = {
                    onCloseClick()
                }
            )
        },
        bottomBar = {
            PreviousOrMigrateButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, start = 24.dp, end = 24.dp)
                    .size(58.dp),
                isNextButtonEnabled = true,
                onPreviousClick = { },
                onMigrateClick = { }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.size(28.dp))
            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "플레이리스트의\n스크린샷을 업로드해주세요.",
                    style = Title1
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = "곡 명, 가수 등 모든 정보가 포함되도록 해주세요!",
                    style = Content1
                )
            }
            Spacer(modifier = Modifier.size(28.dp))

            ScreenShotUploadArea(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0XFFF7F7F7)),
                isHelpTextShow = showUploadHelpTextState,
                onHelpTextCloseClick = { showUploadHelpTextState = false },
                onHelpButtonClick = {
                    //TODO Show Help BottomSheet
                }
            )
        }
    }
}


@Composable
fun ScreenShotUploadArea(
    modifier: Modifier = Modifier,
    isHelpTextShow: Boolean = true,
    onHelpTextCloseClick: () -> Unit = {},
    onHelpButtonClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .size(21.dp)
        )
        ScreenShotUploadButton(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(330.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
        )
        Spacer(
            modifier = Modifier
                .size(39.dp)
        )
        if (isHelpTextShow) {
            ScreenShotUploadHelpText(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(
                        color = colorResource(
                            id = R.color.blue_light
                        )
                    ),
                onCloseClick = { onHelpTextCloseClick() }
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        ShowHelpButton(
            onClick = { onHelpButtonClick() }
        )
    }
}

@Composable
fun ScreenShotUploadButton(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(color = Color(0xFFF8F1FD), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.uploadbutton),
                contentDescription = "upload screenshot",
                modifier = Modifier.size(14.dp)
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Text(text = "추가하기", style = Content2)
    }
}

@Composable
fun ScreenShotUploadHelpText(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = {}
) {

    Row(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "스크린샷 업로드 방법을 확인해보세요",
                style = Content2,
                color = colorResource(id = R.color.blue)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                modifier = Modifier
                    .size(9.dp)
                    .clickable { onCloseClick() },
                painter = painterResource(id = R.drawable.dialogclose),
                contentDescription = null,
                tint = colorResource(id = R.color.blue)
            )
        }
    }
}

@Composable
fun ShowHelpButton(
    onClick: () -> Unit = {}
) {
    Text(
        text = "도움말", style = Content1, fontWeight = FontWeight.SemiBold,
        modifier = Modifier.drawBehind {  }
    )
}

@Preview
@Composable
fun UploadPlaylistScreenShotScreenPreview() {
    UploadPlaylistScreenShotScreen()
}