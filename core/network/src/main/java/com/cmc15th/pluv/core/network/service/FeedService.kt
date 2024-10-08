package com.cmc15th.pluv.core.network.service

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.network.response.CommonResponse
import com.cmc15th.pluv.core.network.response.FeedInfoResponse
import com.cmc15th.pluv.core.network.response.FeedResponse
import com.cmc15th.pluv.core.network.response.ReadSourceMusicResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FeedService {

    @GET("feed")
    suspend fun getAllFeed(): ApiResult<CommonResponse<List<FeedResponse>>>

    @GET("feed/{id}")
    suspend fun getFeedById(@Path("id") id: Long): ApiResult<CommonResponse<FeedInfoResponse>>

    @GET("feed/{id}/music")
    suspend fun getFeedMusics(@Path("id") id: Long): ApiResult<CommonResponse<List<ReadSourceMusicResponse>>>

    @GET("feed/save")
    suspend fun getSavedFeeds(): ApiResult<CommonResponse<List<FeedResponse>>>

    @POST("feed/{id}/save")
    suspend fun bookmarkFeed(@Path("id") id: Long): ApiResult<CommonResponse<String>>

    @DELETE("feed/{id}/save")
    suspend fun unBookmarkFeed(@Path("id") id: Long): ApiResult<CommonResponse<String>>
}