package com.cmc15th.pluv.feature.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.core.designsystem.theme.Content0
import com.cmc15th.pluv.core.designsystem.theme.Content1
import com.cmc15th.pluv.core.designsystem.theme.Gray200
import com.cmc15th.pluv.core.designsystem.theme.Gray600
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.Title2
import com.cmc15th.pluv.core.designsystem.theme.Title3
import com.cmc15th.pluv.feature.mypage.viewmodel.MypageViewModel

@Composable
fun MypageScreen(
    viewModel: MypageViewModel = hiltViewModel(),
    navigateToUserInfo: () -> Unit = {},
    navigateToWebView: (String, String) -> Unit,
    navigateToHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.background(Gray200)
    ) {
        Text(
            text = "마이페이지",
            style = Title3,
            color = Gray800,
            modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp)
        )

        ProfileInfo(
            nickName = uiState.nickName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onUserInfoClick = {
                navigateToUserInfo()
            }
        )

        Spacer(modifier = Modifier.size(20.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            item {
                UserInteractionSection(
                    description = "1:1 문의",
                    onClick = {
                        navigateToWebView("1:1 문의", "https://walla.my/survey/ewFV6AO4W9HhXwM8ilWG")
                    }
                )
                Divider(
                    color = Gray200,
                    thickness = 1.dp,
                )
            }
            item {
                UserInteractionSection(
                    description = "서비스 이용 약관",
                    onClick = {
                        navigateToWebView("서비스 이용 약관", "https://pluv.kro.kr/policy")

                    }
                )
                Divider(
                    color = Gray200,
                    thickness = 1.dp,
                )
            }
            item {
                UserInteractionSection(
                    description = "개인정보처리방침",
                    onClick = {
                        navigateToWebView(
                            "개인정보처리방침",
                            "https://pluv.kro.kr/personal"
                        )
                    }
                )
                Divider(
                    color = Gray200,
                    thickness = 1.dp,
                )
            }
            item {
                UserInteractionSection(
                    description = "로그아웃",
                    onClick = { navigateToHome() }
                )
            }
        }
    }

}

@Composable
fun ProfileInfo(
    modifier: Modifier = Modifier,
    nickName: String,
    onUserInfoClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.default_profile),
                contentDescription = "profile image",
                modifier = Modifier
                    .size(74.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                Text(
                    text = nickName,
                    style = Title2,
                    color = Gray800,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.clickable { onUserInfoClick() }
                ) {
                    Text(
                        text = "회원 정보",
                        style = Content1,
                        color = Gray600,
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.rightarrow),
                        contentDescription = "arrow right",
                        tint = Gray600,
                        modifier = Modifier.size(16.dp)
                    )
                }

            }
        }
    }
}

@Composable
fun UserInteractionSection(
    description: String,
    onClick: () -> Unit
) {
    Text(
        text = description,
        style = Content0,
        color = Gray800,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 20.dp)
    )

}