package com.cmc15th.pluv.core.network.service

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.network.response.CommonResponse
import com.cmc15th.pluv.core.network.response.FeedMusicResponse
import com.cmc15th.pluv.core.network.response.HistoryDetailResponse
import com.cmc15th.pluv.core.network.response.HistoryResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MemberService {
    @POST("member/unregister")
    suspend fun unRegisterMember(): ApiResult<CommonResponse<String>>

    @GET("history/me")
    suspend fun getHistories(): ApiResult<CommonResponse<List<HistoryResponse>>>

    @GET("history/{id}")
    suspend fun getHistoryDetail(@Path("id") historyId: Int): ApiResult<CommonResponse<HistoryDetailResponse>>

    @GET("history/{id}/music/fail")
    suspend fun getTransferSucceedHistoryMusics(@Path("id") historyId: Int): ApiResult<CommonResponse<List<FeedMusicResponse>>>

    @GET("history/{id}/music/success")
    suspend fun getTransferFailedHistoryMusics(@Path("id") historyId: Int): ApiResult<CommonResponse<List<FeedMusicResponse>>>

    @GET("history/recent")
    suspend fun getRecentHistory(): ApiResult<CommonResponse<HistoryDetailResponse>>
}