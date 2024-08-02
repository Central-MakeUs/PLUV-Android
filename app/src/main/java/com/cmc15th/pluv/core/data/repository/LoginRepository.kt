package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.JwtToken
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun googleLogin(idToken: String): Flow<ApiResult<JwtToken>>
}