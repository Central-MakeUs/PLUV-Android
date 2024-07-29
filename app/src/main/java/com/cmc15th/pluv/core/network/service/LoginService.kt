package com.cmc15th.pluv.core.network.service

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.network.request.LoginRequest
import com.cmc15th.pluv.core.network.response.CommonResponse
import com.cmc15th.pluv.core.network.response.LoginResponse
import com.cmc15th.pluv.domain.model.PlayListApp
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {
    @POST("{oauth}/login")
    suspend fun pluvLogin(
        @Path("oauth") oauth: PlayListApp,
        @Body loginRequest: LoginRequest
    ): ApiResult<CommonResponse<LoginResponse>>
}