package com.cmc15th

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc15th.pluv.core.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginState: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val loginState: StateFlow<Boolean?> = _loginState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(500L)
            checkIsLoggedIn()
        }
    }

    private fun checkIsLoggedIn() {
        viewModelScope.launch {
            authRepository.getAccessToken().collect { token ->
                Log.d("MainActivityViewModel", "checkIsLoggedIn: $token")
                if (token.isNotBlank()) {
                    _loginState.update { true }
                } else {
                    _loginState.update { false }
                }
            }
        }
    }
}