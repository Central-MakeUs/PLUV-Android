package com.cmc15th.pluv.ui.feed.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc15th.pluv.core.data.repository.FeedRepository
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
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<FeedUiState> = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<FeedUiEvent> = MutableSharedFlow()

    private val _uiEffect: Channel<FeedUiEffect> = Channel()
    val uiEffect: Flow<FeedUiEffect> = _uiEffect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    fun setEvent(event: FeedUiEvent) {
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

    private fun handleEvent(event: FeedUiEvent) {
        when (event) {
            is FeedUiEvent.OnLoadAllFeeds -> {
                getAllFeeds()
            }
            is FeedUiEvent.SelectFeed -> {
                getFeedById(event.feedId)
                getFeedMusics(event.feedId)
            }
            is FeedUiEvent.ToggleBookmark -> {
                val bookmarkState = _uiState.value.feedInfo.isBookMarked
                // 먼저 UI를 업데이트 한 후에 네트워크 요청 보내고, 성공/실패에 따라 다시 UI 업데이트
                _uiState.update {
                    it.copy(feedInfo = it.feedInfo.copy(isBookMarked = !bookmarkState))
                }
                when (bookmarkState) {
                    true -> unBookmarkFeed(event.feedId)
                    false -> bookmarkFeed(event.feedId)
                }
            }
            is FeedUiEvent.OnLoadSavedFeeds -> {
                getSavedFeeds()
            }
        }
    }

    private fun sendEffect(effect: FeedUiEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

    private fun getAllFeeds() {
        viewModelScope.launch {
            feedRepository.getAllFeed().collect { result ->
                result.onSuccess { feed ->
                    Log.d(TAG, "getAllFeeds: success $feed")
                    _uiState.update {
                        it.copy(allFeeds = feed)
                    }
                }

                result.onFailure { code, msg ->
                    sendEffect(FeedUiEffect.OnFailure(msg))
                    Log.d(TAG, "getAllFeeds: $code $msg")
                }
            }
        }
    }
    
    private fun getFeedById(id: Long) {
        viewModelScope.launch {
            feedRepository.getFeedById(id).collect { result ->
                result.onSuccess { feedInfo ->
                    _uiState.update {
                        it.copy(feedInfo = feedInfo)
                    }
                }
                result.onFailure { code, msg ->
                    sendEffect(FeedUiEffect.OnFailure(msg))
                }
            }
        }
    }

    private fun getFeedMusics(id: Long) {
        viewModelScope.launch {
            feedRepository.getFeedMusics(id).collect { result ->
                result.onSuccess { feedMusics ->
                    _uiState.update {
                        it.copy(feedMusics = feedMusics)
                    }
                }
                result.onFailure { code, msg ->
                    sendEffect(FeedUiEffect.OnFailure(msg))
                }
            }
        }
    }

    private fun bookmarkFeed(id: Long) {
        viewModelScope.launch {
            feedRepository.bookmarkFeed(id).collect { result ->
                result.onSuccess {
                    _uiState.update {
                        it.copy(feedInfo = it.feedInfo.copy(isBookMarked = true))
                    }
                    sendEffect(FeedUiEffect.OnSaveSuccess("플레이리스트를 저장했어요"))
                }
                result.onFailure { code, msg ->
                    _uiState.update {
                        it.copy(feedInfo = it.feedInfo.copy(isBookMarked = false))
                    }
                    sendEffect(FeedUiEffect.OnFailure("플레이리스트 저장에 실패했어요"))
                }
            }
        }
    }

    private fun unBookmarkFeed(feedId: Long) {
        viewModelScope.launch {
            feedRepository.unBookmarkFeed(feedId).collect { result ->
                result.onSuccess {
                    _uiState.update {
                        it.copy(feedInfo = it.feedInfo.copy(isBookMarked = false))
                    }
                }
                result.onFailure { code, msg ->
                    _uiState.update {
                        it.copy(feedInfo = it.feedInfo.copy(isBookMarked = true))
                    }
                    sendEffect(FeedUiEffect.OnFailure("플레이리스트 저장에 실패했어요"))
                }
            }
        }
    }

    private fun getSavedFeeds() {
        viewModelScope.launch {
            feedRepository.getSavedFeeds().collect { result ->
                result.onSuccess { savedFeeds ->
                    _uiState.update {
                        it.copy(allFeeds = savedFeeds)
                    }
                }
                result.onFailure { _, msg ->
                    sendEffect(FeedUiEffect.OnFailure(msg))
                }
            }
        }
    }

    companion object {
        private const val TAG = "FeedViewModel"
    }
}