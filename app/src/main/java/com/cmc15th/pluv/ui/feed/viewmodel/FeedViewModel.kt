package com.cmc15th.pluv.ui.feed.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc15th.pluv.core.data.repository.FeedRepository
import com.cmc15th.pluv.core.model.Feed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    init {
        subscribeEvents()
        getAllFeeds()
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
            is FeedUiEvent.SelectFeed -> {
                _uiState.update {
                    it.copy(selectedFeed = it.allFeeds.find { feed -> feed.id == event.feedId } ?: Feed() )
                }
            }
        }
    }

    private fun getAllFeeds() {
        viewModelScope.launch {
            feedRepository.getAllFeed().collect { result ->
                result.onSuccess { feed ->
                    Log.d(TAG, "getAllFeeds: success $feed")
                    _uiState.update {
                        it.copy(allFeeds = feed.filterIndexed {
                            index, _ -> index > 6
                        })
                    }
                }

                result.onFailure { code, msg ->
                    Log.d(TAG, "getAllFeeds: $code $msg")
                }
            }
        }
    }
    companion object {
        private const val TAG = "FeedViewModel"
    }
}