package com.cmc15th.pluv.core.network.service

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.network.request.GoogleLoginRequest
import com.cmc15th.pluv.core.network.response.CommonResponse
import com.cmc15th.pluv.core.network.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/login/google")
    suspend fun googleLogin(
        @Body googleLoginRequest: GoogleLoginRequest
    ): ApiResult<CommonResponse<LoginResponse>>

    @POST("/login/spotify")
    suspend fun spotifyLogin(
        @Body accessToken: String
    ): ApiResult<CommonResponse<LoginResponse>>
}