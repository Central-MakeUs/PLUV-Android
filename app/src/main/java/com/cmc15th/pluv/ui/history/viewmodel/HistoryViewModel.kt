package com.cmc15th.pluv.ui.history.viewmodel

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
            is HistoryUiEvent.OnHistoryClicked -> {
                getHistoryDetail(event.historyId)
            }
        }
    }

    private fun sendEffect(effect: HistoryUiEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

    private fun getHistoryDetail(historyId: Int) {
        viewModelScope.launch {
            memberRepository.getHistoryDetail(historyId).collect { result ->
                result.onSuccess { history ->
                    _uiState.update {
                        it.copy(selectedHistory = history)
                    }
                }

                result.onFailure { _, msg ->
                    sendEffect(HistoryUiEffect.OnFailure(msg))
                }
            }
        }
    }
}