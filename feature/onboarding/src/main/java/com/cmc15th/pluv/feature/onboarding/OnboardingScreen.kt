package com.cmc15th.pluv.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.core.designsystem.component.PLUVButton
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Gray300
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.Title1
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(
    navigateToLogin: () -> Unit = {},
) {
    val pagerState = com.google.accompanist.pager.rememberPagerState(initialPage = 0)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "건너뛰기",
                style = Content1,
                color = Color(0xFF2E81FF),
                modifier = Modifier
                    .padding(vertical = 15.dp, horizontal = 24.dp)
                    .align(Alignment.End)
                    .clickable {
                        navigateToLogin()
                    }
            )
            com.google.accompanist.pager.HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth(),
                state = pagerState,
                count = 4,
            ) {
                when (it) {
                    0 -> OnboardingItem(
                        descriptionRes = R.string.onboarding_description_1,
                        imageRes = R.drawable.onboarding1
                    )
                    1 -> OnboardingItem(
                        descriptionRes = R.string.onboarding_description_2,
                        imageRes = R.drawable.onboarding2
                    )
                    2 -> OnboardingItem(
                        descriptionRes = R.string.onboarding_description_3,
                        imageRes = R.drawable.onboarding3
                    )
                    3 -> OnboardingItem(
                        descriptionRes = R.string.onboarding_description_4,
                        imageRes = R.drawable.onboarding4
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .padding(vertical = 32.dp),
                activeColor = Gray800, // 활성화된 인디케이터의 색상
                inactiveColor = Gray300, // 비활성화된 인디케이터의 색상
                indicatorWidth = 6.dp, // 인디케이터의 너비
                indicatorHeight = 6.dp // 인디케이터의 높이
            )
        }



        // 마지막 페이지에만 버튼을 표시
        if (pagerState.currentPage == pagerState.pageCount - 1) {
            PLUVButton(
                onClick = {
                    navigateToLogin()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 32.dp),
                containerColor = Gray800,
                contentColor = Color.White,
                content = {
                    Text(text = "시작하기", style = Content1)
                }
            )
        }
    }
}

@Composable
fun OnboardingItem(
    modifier: Modifier = Modifier,
    descriptionRes: Int,
    imageRes: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = descriptionRes),
            style = Title1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "onboarding image",
            modifier = Modifier.padding(horizontal = 55.dp)
        )
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen()
}
