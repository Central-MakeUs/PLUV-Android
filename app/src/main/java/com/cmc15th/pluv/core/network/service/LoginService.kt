package com.cmc15th.pluv.core.network.service

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.network.request.GoogleLoginRequest
import com.cmc15th.pluv.core.network.request.SpotifyLoginRequest
import com.cmc15th.pluv.core.network.response.CommonResponse
import com.cmc15th.pluv.core.network.response.GoogleAccessTokenResponse
import com.cmc15th.pluv.core.network.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @POST("/login/google")
    suspend fun googleLogin(
        @Body googleLoginRequest: GoogleLoginRequest
    ): ApiResult<CommonResponse<LoginResponse>>

    @POST("/login/spotify")
    suspend fun spotifyLogin(
        @Body accessToken: SpotifyLoginRequest
    ): ApiResult<CommonResponse<LoginResponse>>

    @GET("/oauth/youtube/token")
    suspend fun getGoogleAccessToken(
        @Query("code") code: String
    ): ApiResult<CommonResponse<GoogleAccessTokenResponse>>
}