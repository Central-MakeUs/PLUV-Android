package com.cmc15th.pluv.core.network.service

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.network.response.CommonResponse
import com.cmc15th.pluv.core.network.response.FeedResponse
import retrofit2.http.GET

interface FeedService {

    @GET("feed")
    suspend fun getAllFeed(): ApiResult<CommonResponse<List<FeedResponse>>>
}