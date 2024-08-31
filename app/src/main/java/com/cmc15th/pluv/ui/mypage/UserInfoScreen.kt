package com.cmc15th.pluv.ui.mypage

import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.cmc15th.pluv.core.designsystem.theme.Gray200
import com.cmc15th.pluv.core.designsystem.theme.Gray300
import com.cmc15th.pluv.core.designsystem.theme.Gray600
import com.cmc15th.pluv.core.designsystem.theme.Gray800
import com.cmc15th.pluv.core.designsystem.theme.Title4
import com.cmc15th.pluv.core.model.SocialAccount
import com.cmc15th.pluv.ui.common.contract.GoogleApiContract
import com.cmc15th.pluv.ui.common.contract.SpotifyAuthContract
import com.cmc15th.pluv.ui.login.GoogleLoginButton
import com.cmc15th.pluv.ui.login.SpotifyLoginButton
import com.cmc15th.pluv.ui.mypage.viewmodel.MypageUiEffect
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

    val googleLoginResultLauncher = rememberLauncherForActivityResult(
        contract = GoogleApiContract()
    ) { task ->
        viewModel.setEvent(MypageUiEvent.OnAddGoogleAccount(task))
    }

    val spotifyLoginResultLauncher = rememberLauncherForActivityResult(
        contract = SpotifyAuthContract()
    ) { task ->
        viewModel.setEvent(MypageUiEvent.OnAddSpotifyAccount(task))
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is MypageUiEffect.OnSuccess -> {
                    showSnackBar(effect.message)
                }

                is MypageUiEffect.OnFailure -> {
                    showSnackBar(effect.message)
                }

                else -> {}
            }
        }
    }

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
                },
                onNickNameChange = {
                    if (it.length <= 10) {
                        viewModel.setEvent(MypageUiEvent.OnNickNameChanged(it))
                    }
                },
                modifiedNickName = uiState.modifiedNickName,
            )
        }

        UserInfoRowArea(title = "연결된 소셜 계정") {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                uiState.integratedSocialSocialAccount.forEach {
                    Text(
                        text = it.serviceName,
                        style = Content0,
                        color = Gray800,
                        modifier = Modifier.padding(vertical = 11.dp)
                    )
                }
            }
        }

        UserInfoRowArea(title = "소셜 계정 추가 연결하기") {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                val notIntegratedSocialAccounts =
                    SocialAccount.entries.filter { uiState.integratedSocialSocialAccount.contains(it) }
                if (notIntegratedSocialAccounts.contains(SocialAccount.google).not()) {
                    GoogleLoginButton(
                        modifier = Modifier.border(
                            1.dp, Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp)
                        ),
                        description = "Google 연결하기",
                        onClick = { googleLoginResultLauncher.launch(1) }
                    )
                }
                if (notIntegratedSocialAccounts.contains(SocialAccount.spotify).not()) {
                    SpotifyLoginButton(
                        description = "Spotify 연결하기",
                        onClick = { spotifyLoginResultLauncher.launch(1) }
                    )
                }
            }
        }
        Text(
            text = "회원 탈퇴하기",
            style = Content0,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navigateToUnregister() }
                .padding(vertical = 20.dp, horizontal = 24.dp)
        )
    }
}

@Composable
fun UserInfoRowArea(
    title: String,
    isVisibleDivider: Boolean = true,
    content: @Composable () -> Unit
) {
    Column(
        Modifier.fillMaxWidth()
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
        if (isVisibleDivider) {
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = Gray200,
                thickness = 1.dp
            )
        }
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