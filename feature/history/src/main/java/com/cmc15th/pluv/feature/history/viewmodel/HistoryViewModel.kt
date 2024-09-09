package com.cmc15th.pluv.feature.history.viewmodel

import android.util.Log
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
class HistoryViewModel @Inject constructor(
    private val memberRepository: MemberRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<HistoryUiState> = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<HistoryUiEvent> = MutableSharedFlow()

    private val _uiEffect: Channel<HistoryUiEffect> = Channel()
    val uiEffect: Flow<HistoryUiEffect> = _uiEffect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    fun setEvent(event: HistoryUiEvent) {
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

    private fun handleEvent(event: HistoryUiEvent) {
        when (event) {
            is HistoryUiEvent.OnLoadHistories -> {
                getHistories()
            }

            is HistoryUiEvent.OnHistoryClicked -> {
                getHistoryDetail(event.historyId)
                getTransferSuccessMusics(event.historyId)
                getTransferFailMusics(event.historyId)
            }
        }
    }

    private fun sendEffect(effect: HistoryUiEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

    private fun getHistories() {
        viewModelScope.launch {
            memberRepository.getHistories().collect { result ->
                result.onSuccess { histories ->
                    Log.d(TAG, "getHistories: $histories")
                    _uiState.update {
                        it.copy(histories = histories)
                    }
                }

                result.onFailure { _, msg ->
                    Log.d(TAG, "getHistories: $msg")
                    sendEffect(HistoryUiEffect.OnFailure(msg))
                }
            }
        }
    }

    private fun getHistoryDetail(historyId: Long) {
        viewModelScope.launch {
            memberRepository.getHistoryDetail(historyId).collect { result ->
                result.onSuccess { history ->
                    _uiState.update {
                        Log.d(TAG, "getHistoryDetail:  $history")
                        it.copy(selectedHistory = history)
                    }
                }

                result.onFailure { _, msg ->
                    Log.d(TAG, "getHistoryDetail: $msg")
                    sendEffect(HistoryUiEffect.OnFailure(msg))
                }
            }
        }
    }

    private fun getTransferSuccessMusics(historyId: Long) {
        viewModelScope.launch {
            memberRepository.getTransferSucceedHistoryMusics(historyId).collect { result ->
                result.onSuccess { musics ->
                    _uiState.update {
                        Log.d(TAG, "getTransferSuccessMusics: $musics")
                        it.copy(transferSuccessMusics = musics)
                    }
                }

                result.onFailure { _, msg ->
                    sendEffect(HistoryUiEffect.OnFailure(msg))
                }
            }
        }
    }

    private fun getTransferFailMusics(historyId: Long) {
        viewModelScope.launch {
            memberRepository.getTransferFailedHistoryMusics(historyId).collect { result ->
                result.onSuccess { musics ->
                    _uiState.update {
                        Log.d(TAG, "getTransferFailMusics: $musics ")
                        it.copy(transferFailMusics = musics)
                    }
                }

                result.onFailure { _, msg ->
                    sendEffect(HistoryUiEffect.OnFailure(msg))
                }
            }
        }
    }
    
    companion object {
        private const val TAG = "HistoryViewModel"
    }
}