package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.Feed
import com.cmc15th.pluv.core.model.FeedInfo
import com.cmc15th.pluv.core.model.SourceMusic
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun getAllFeed(): Flow<ApiResult<List<Feed>>>

    fun getFeedById(id: Long): Flow<ApiResult<FeedInfo>>

    fun getFeedMusics(id: Long): Flow<ApiResult<List<SourceMusic>>>

    fun getSavedFeeds(): Flow<ApiResult<List<Feed>>>

    fun bookmarkFeed(id: Long): Flow<ApiResult<String>>

    fun unBookmarkFeed(id: Long): Flow<ApiResult<String>>
}