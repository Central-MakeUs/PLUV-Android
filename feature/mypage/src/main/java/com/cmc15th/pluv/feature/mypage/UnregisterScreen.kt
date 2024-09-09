package com.cmc15th.pluv.feature.mypage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.core.designsystem.R
import com.cmc15th.pluv.core.designsystem.component.PLUVButton
import com.cmc15th.pluv.core.designsystem.component.TopAppBar
import com.cmc15th.pluv.core.designsystem.theme.Content0
import com.cmc15th.pluv.core.designsystem.theme.Title3
import com.cmc15th.pluv.feature.mypage.viewmodel.MypageUiEffect
import com.cmc15th.pluv.feature.mypage.viewmodel.MypageUiEvent
import com.cmc15th.pluv.feature.mypage.viewmodel.MypageViewModel

@Composable
fun UnregisterScreen(
    viewModel: MypageViewModel = hiltViewModel(),
    showSnackBar: (String) -> Unit = {},
    onBackClicked: () -> Unit = {},
    navigateToLogin: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is MypageUiEffect.OnFailure -> {
                    showSnackBar(effect.message)
                }
                is MypageUiEffect.NavigateToLogin -> {
                    showSnackBar("회원 탈퇴가 완료되었습니다.")
                    navigateToLogin()
                }
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            TopAppBar(description = "회원 탈퇴하기", onBackClick = onBackClicked)

            Text(text = "PLUV를 정말 탈퇴하시나요?", style = Title3, modifier = Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp))

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "계정 삭제 시, 회원 정보와 모든 이용 내역(내 플레이리스트 및 저장한 플레이리스트)이 삭제되며 복구가 불가능합니다.", modifier = Modifier.padding(24.dp))

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter =
                    if (uiState.isUnregisterChecked) painterResource(id = R.drawable.checked_button_ractangle)
                    else painterResource(id = R.drawable.unchecked_button_ractangle),
                    contentDescription = "check button",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { viewModel.setEvent(MypageUiEvent.OnUnregisterChecked)
                        },
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.size(10.dp))

                Text(text = "위 사항을 모두 확인했으며, 이에 동의합니다.", modifier = Modifier.padding(8.dp))
            }
        }

        PLUVButton(
            onClick = { viewModel.setEvent(MypageUiEvent.OnUnRegisterMemberClicked) },
            enabled = uiState.isUnregisterChecked,
            containerColor = Color.Black, contentColor = Color.White,
            modifier = Modifier.fillMaxWidth().padding(24.dp)) {
            Text(text = "회원 탈퇴하기", style = Content0)
        }
    }
}