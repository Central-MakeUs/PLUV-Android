package com.cmc15th.pluv.ui.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<MypageUiState> = MutableStateFlow(MypageUiState())
    val uiState: StateFlow<MypageUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<MypageUiEvent> = MutableSharedFlow()

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
}