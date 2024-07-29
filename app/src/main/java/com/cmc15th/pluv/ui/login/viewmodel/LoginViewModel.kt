package com.cmc15th.pluv.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc15th.pluv.core.data.repository.LoginRepository
import com.cmc15th.pluv.domain.model.PlayListApp
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
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
                //TODO 스포티파이 로그인
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
            sendEffect(LoginUiEffect.OnLoginFailure("task is null"))
            return
        }

        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken

            if (idToken.isNullOrEmpty()) {
                sendEffect(LoginUiEffect.OnLoginFailure("null"))
                return
            }

            getAccessTokenBySocialToken(PlayListApp.YOUTUBE_MUSIC, account.idToken!!)

        } catch (e: ApiException) {
            Log.e(TAG, "Google sign in failed: ${e.statusCode} + ${e.message}")
            sendEffect(LoginUiEffect.OnLoginFailure("Google sign in failed: ${e.statusCode} + ${e.message}"))
        }
    }

    private fun getAccessTokenBySocialToken(oauth: PlayListApp, token: String) {
        viewModelScope.launch {
            loginRepository.login(oauth, token).collect { result ->
                result.onSuccess {
                    //TODO datastore에 토큰 저장
                }

                result.onFailure { i, s ->

                }

            }
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}