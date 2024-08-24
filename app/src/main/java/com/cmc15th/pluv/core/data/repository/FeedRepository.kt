package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.Feed
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun getAllFeed(): Flow<ApiResult<List<Feed>>>

    fun getSavedFeed()

    fun saveFeed()
}