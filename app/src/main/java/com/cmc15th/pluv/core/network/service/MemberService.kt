package com.cmc15th.pluv.core.network.service

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.network.response.CommonResponse
import retrofit2.http.POST

interface MemberService {
    @POST("member/unregister")
    suspend fun unRegisterMember(): ApiResult<CommonResponse<String>>
}