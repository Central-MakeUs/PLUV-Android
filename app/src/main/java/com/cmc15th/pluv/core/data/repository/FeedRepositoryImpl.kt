package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.data.mapper.toFeed
import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.Feed
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

    override fun getSavedFeed() {
        TODO("Not yet implemented")
    }

    override fun saveFeed() {
        TODO("Not yet implemented")
    }

}