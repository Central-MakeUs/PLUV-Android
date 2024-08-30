package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.GoogleAccessToken
import com.cmc15th.pluv.core.model.JwtToken
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun googleLogin(idToken: String): Flow<ApiResult<JwtToken>>

    fun getGoogleAccessToken(authCode: String): Flow<ApiResult<GoogleAccessToken>>

    fun spotifyLogin(accessToken: String): Flow<ApiResult<JwtToken>>

    fun addGoogleAccount(idToken: String): Flow<ApiResult<String>>

    fun addSpotifyAccount(accessToken: String): Flow<ApiResult<String>>
}