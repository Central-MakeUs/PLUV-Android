package com.cmc15th.pluv.feature.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc15th.pluv.core.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiEffect: Channel<SplashUiEffect> = Channel()
    val uiEffect: Flow<SplashUiEffect> = _uiEffect.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(500L)
            checkIsLoggedIn()
        }
    }

    private fun sendEffect(effect: SplashUiEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

    private fun checkIsLoggedIn() {
        viewModelScope.launch {
            authRepository.getAccessToken().collect { token ->
                if (token.isNotBlank()) {
                    sendEffect(SplashUiEffect.IsLoggedIn(true))
                } else {
                    sendEffect(SplashUiEffect.IsLoggedIn(false))
                }
            }
        }
    }
}