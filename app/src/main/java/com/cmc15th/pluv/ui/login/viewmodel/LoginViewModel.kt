package com.cmc15th.pluv.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc15th.pluv.core.data.repository.AuthRepository
import com.cmc15th.pluv.core.data.repository.LoginRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.spotify.sdk.android.auth.AuthorizationResponse
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
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<LoginUiEvent> = MutableSharedFlow()

    private val _uiEffect: Channel<LoginUiEffect> = Channel()
    val uiEffect: Flow<LoginUiEffect> = _uiEffect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    fun setEvent(event: LoginUiEvent) {
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

    private fun handleEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.GoogleLogin -> {
                handleGoogleSignInResult(event.task)
            }
            is LoginUiEvent.SpotifyLogin -> {
                handleSpotifyLoginResult(event.task)
            }
            is LoginUiEvent.AppleLogin -> {
                //TODO 애플 로그인
            }
        }
    }

    private fun sendEffect(effect: LoginUiEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

    private fun handleGoogleSignInResult(task: Task<GoogleSignInAccount>?) {
        if (task == null) {
            Log.d(TAG, "handleGoogleSignInResult:   task is null")
            sendEffect(LoginUiEffect.OnLoginFailure("task is null"))
            return
        }

        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken

            if (idToken.isNullOrEmpty()) {
                Log.d(TAG, "handleGoogleSignInResult:   idToken is null")
                sendEffect(LoginUiEffect.OnLoginFailure("null"))
                return
            }

            googleLogin(account.idToken!!)

        } catch (e: ApiException) {
            Log.e(TAG, "Google sign in failed: ${e.statusCode} + ${e.message}")
            sendEffect(LoginUiEffect.OnLoginFailure("Google sign in failed: ${e.statusCode} + ${e.message}"))
        }
    }

    private fun handleSpotifyLoginResult(task: AuthorizationResponse) {
        when (task.type) {
            AuthorizationResponse.Type.TOKEN -> {
                val accessToken = task.accessToken
                Log.d(TAG, "handleSpotifyLoginResult:  $accessToken ")
                spotifyLogin(accessToken)
            }

            else -> {
                val error = task.error
                Log.e(TAG, "handleSpotifyLoginResult: $error")
                sendEffect(LoginUiEffect.OnLoginFailure(error))
            }
        }
    }

    private fun googleLogin(token: String) {
        viewModelScope.launch {
            setLoadingState(true)
            loginRepository.googleLogin(token).collect { result ->
                Log.d(TAG, "getAccessTokenBySocialToken: $token")
                result.onSuccess {
                    saveJwtToken(it.accessToken)
                    sendEffect(LoginUiEffect.OnLoginSuccess)
                    setLoadingState(false)
                }

                result.onFailure { i, s ->
                    sendEffect(LoginUiEffect.OnLoginFailure(s ?: "알 수 없는 오류가 발생하였습니다."))
                    setLoadingState(false)
                }
            }
        }
    }

    private fun spotifyLogin(token: String) {
        viewModelScope.launch {
            setLoadingState(true)
            loginRepository.spotifyLogin(token).collect { result ->
                result.onSuccess {
                    saveJwtToken(it.accessToken)
                    sendEffect(LoginUiEffect.OnLoginSuccess)
                    setLoadingState(false)

                }

                result.onFailure { i, s ->
                    sendEffect(LoginUiEffect.OnLoginFailure(s ?: "알 수 없는 오류가 발생하였습니다."))
                    setLoadingState(false)
                }
            }
        }
    }

    private fun saveJwtToken(token: String) {
        viewModelScope.launch {
            Log.d(TAG, "saveJwtToken: $token")
            authRepository.saveAccessToken(token)
        }
    }

    private fun setLoadingState(state: Boolean) {
        _uiState.update {
            it.copy(isLoading = state)
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}