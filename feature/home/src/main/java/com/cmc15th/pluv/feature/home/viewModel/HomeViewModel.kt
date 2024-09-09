package com.cmc15th.pluv.feature.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc15th.pluv.core.data.repository.FeedRepository
import com.cmc15th.pluv.core.data.repository.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val feedRepository: FeedRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getHistories()
        getSavedFeed()
    }

    private fun getHistories() {
        viewModelScope.launch {
            memberRepository.getHistories().collect { result ->
                result.onSuccess { histories ->
                    _uiState.update {
                        it.copy(
                            historiesThumbnailUrl = histories.take(7)
                                .map { history -> history.id to history.imageUrl })
                    }
                }
            }
        }
    }

    private fun getSavedFeed() {
        viewModelScope.launch {
            feedRepository.getSavedFeeds().collect { result ->
                result.onSuccess { feeds ->
                    _uiState.update {
                        it.copy(
                            savedFeedsThumbnailUrl = feeds.take(7)
                                .map { feed -> feed.id to feed.thumbNailUrl })
                    }
                }
            }
        }
    }
}