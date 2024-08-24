package com.cmc15th.pluv.ui.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc15th.pluv.core.data.repository.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val memberRepository: MemberRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<MypageUiState> = MutableStateFlow(MypageUiState())
    val uiState: StateFlow<MypageUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<MypageUiEvent> = MutableSharedFlow()

    private val _uiEffect: Channel<MypageUiEffect> = Channel()
    val uiEffect: Flow<MypageUiEffect> = _uiEffect.receiveAsFlow()



    init {
        subscribeEvents()
    }

    fun setEvent(event: MypageUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            _uiEvent.collect {
                handleEvent(it)
            }
        }
    }

    private fun handleEvent(event: MypageUiEvent) {
        when (event) {
            is MypageUiEvent.OnModifyNicknameClicked -> {
                setNicknameModifying()
            }
            is MypageUiEvent.OnNickNameChanged -> {
                onChangeNickname(event.nickname)
            }
            is MypageUiEvent.OnChangeNicknameClicked -> {
                setNicknameModifying()
                changeNickname()
                //TODO 닉네임 변경 API 호출
            }
            is MypageUiEvent.OnUnRegisterMemberClicked -> {
                unRegisterMember()
            }
            is MypageUiEvent.OnUnregisterChecked -> {
                _uiState.update { it.copy(isUnregisterChecked = it.isUnregisterChecked.not()) }
            }
        }
    }

    private fun sendEffect(effect: MypageUiEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

    private fun setNicknameModifying() {
        _uiState.update {
            it.copy(isNicknameModifying = _uiState.value.isNicknameModifying.not())
        }
    }

    private fun onChangeNickname(nickname: String) {
        _uiState.update {
            it.copy(modifiedNickName = nickname)
        }
    }

    private fun changeNickname() {
        _uiState.update {
            it.copy(nickName = _uiState.value.modifiedNickName)
        }
    }

    private fun unRegisterMember() {
        viewModelScope.launch {
            memberRepository.unRegisterMember().collect { result ->
                result.onSuccess {
                    sendEffect(MypageUiEffect.NavigateToLogin)
                }

                result.onFailure { i, s ->
                    sendEffect(MypageUiEffect.OnFailure(s!!))
                }
            }
        }
    }
}