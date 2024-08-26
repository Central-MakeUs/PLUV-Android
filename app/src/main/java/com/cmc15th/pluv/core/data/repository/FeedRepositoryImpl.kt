package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.data.mapper.toFeed
import com.cmc15th.pluv.core.data.mapper.toFeedInfo
import com.cmc15th.pluv.core.data.mapper.toFeedMusic
import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.Feed
import com.cmc15th.pluv.core.model.FeedInfo
import com.cmc15th.pluv.core.model.FeedMusic
import com.cmc15th.pluv.core.network.service.FeedService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedService: FeedService
) : FeedRepository {
    override fun getAllFeed(): Flow<ApiResult<List<Feed>>> = flow {
        emit(
            feedService.getAllFeed().map { response -> response.data.map { it.toFeed() } }
        )
    }.flowOn(Dispatchers.IO)

    override fun getFeedById(id: Long): Flow<ApiResult<FeedInfo>> = flow {
        emit(
            feedService.getFeedById(id).map { response -> response.data.toFeedInfo() }
        )
    }.flowOn(Dispatchers.IO)

    override fun getFeedMusics(id: Long): Flow<ApiResult<List<FeedMusic>>> = flow {
        emit(
            feedService.getFeedMusics(id).map { response -> response.data.map { it.toFeedMusic() } }
        )
    }.flowOn(Dispatchers.IO)

    override fun getSavedFeeds(): Flow<ApiResult<List<Feed>>> = flow {
        emit(
            feedService.getSavedFeeds().map { response -> response.data.map { it.toFeed() } }
        )
    }.flowOn(Dispatchers.IO)

    override fun bookmarkFeed(id: Long): Flow<ApiResult<String>> = flow {
        emit(
            feedService.bookmarkFeed(id).map { response -> response.data }
        )
    }.flowOn(Dispatchers.IO)

    override fun unBookmarkFeed(id: Long): Flow<ApiResult<String>> = flow {
        emit(
            feedService.unBookmarkFeed(id).map { response -> response.data }
        )
    }.flowOn(Dispatchers.IO)

}