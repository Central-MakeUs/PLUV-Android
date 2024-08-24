package com.cmc15th.pluv.ui.mypage

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cmc15th.pluv.core.designsystem.component.PLUVButton
import com.cmc15th.pluv.core.designsystem.component.PLUVTextField
import com.cmc15th.pluv.core.designsystem.component.TopAppBar
import com.cmc15th.pluv.core.designsystem.theme.Content0
import com.cmc15th.pluv.core.designsystem.theme.Content2
import com.cmc15th.pluv.core.designsystem.theme.Gray300
import com.cmc15th.pluv.core.designsystem.theme.Gray600
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.Title4
import com.cmc15th.pluv.ui.mypage.viewmodel.MypageUiEvent
import com.cmc15th.pluv.ui.mypage.viewmodel.MypageViewModel

@Composable
fun UserInfoScreen(
    viewModel: MypageViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    showSnackBar: (String) -> Unit = {},
    navigateToUnregister: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column {
        TopAppBar(description = "회원 정보", onBackClick = { onBackClick() })
        UserInfoRowArea(title = "닉네임") {
            NickNameArea(
                nickName = uiState.nickName,
                isNicknameModifying = uiState.isNicknameModifying,
                onModifyClick = { viewModel.setEvent(MypageUiEvent.OnModifyNicknameClicked) },
                onModifyCompleteClick = {
                    if (uiState.modifiedNickName.isEmpty()) {
                        showSnackBar("닉네임을 입력해주세요.")
                        return@NickNameArea
                    }
                    viewModel.setEvent(MypageUiEvent.OnChangeNicknameClicked)
                    //FIXME 서버 구현 후 UiEffect에 따라 성공/실패 메세지로 변경
                    showSnackBar("닉네임이 변경됐어요!.")
                },
                onNickNameChange = {
                    viewModel.setEvent(MypageUiEvent.OnNickNameChanged(it))
                },
                modifiedNickName = uiState.modifiedNickName,
            )
        }
        Text(
            text = "회원 탈퇴하기",
            style = Content0,
            modifier = Modifier
                .clickable { navigateToUnregister() }
                .padding(horizontal = 24.dp)
        )
    }
}

@Composable
fun UserInfoRowArea(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Text(text = title, style = Content2, color = Gray600)
        Spacer(modifier = Modifier.height(10.dp))
        content()
    }
}

@Composable
fun ModifyNickNameArea(
    modifier: Modifier = Modifier,
    modifiedNickName: String,
    onNickNameChange: (String) -> Unit,
    onModifyCompleteClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PLUVTextField(
            modifier = Modifier
                .weight(4f)
                .border(1.dp, Gray300, RoundedCornerShape(8.dp)),
            value = modifiedNickName,
            onValueChange = { onNickNameChange(it) }
        )
        PLUVButton(
            onClick = { onModifyCompleteClick() },
            containerColor = Color.Black,
            contentColor = Color.White,
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 15.dp)
        ) {
            Text(
                text = "완료",
                style = Content2,
            )
        }
    }
}

@Composable
fun ShowNickNameArea(
    modifier: Modifier = Modifier,
    nickName: String,
    onModifyClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = nickName, style = Title4, color = Gray800)
        PLUVButton(
            onClick = { onModifyClick() },
            containerColor = Color.White,
            contentColor = Gray800,
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 15.dp),
            modifier = Modifier.border(1.dp, Gray300, RoundedCornerShape(8.dp))
        ) {
            Text(
                text = "수정",
                style = Content2,
            )
        }
    }
}

@Composable
fun NickNameArea(
    modifier: Modifier = Modifier,
    nickName: String,
    modifiedNickName: String,
    onNickNameChange: (String) -> Unit,
    isNicknameModifying: Boolean = false,
    onModifyClick: () -> Unit,
    onModifyCompleteClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (isNicknameModifying) {
            ModifyNickNameArea(
                modifiedNickName = modifiedNickName,
                onNickNameChange = onNickNameChange,
                onModifyCompleteClick = onModifyCompleteClick
            )
        } else {
            ShowNickNameArea(
                nickName = nickName,
                onModifyClick = onModifyClick
            )
        }
    }
}

@Preview
@Composable
fun UserInfoScreenPreview() {
    UserInfoScreen()
}