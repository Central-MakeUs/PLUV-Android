package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.JwtToken
import com.cmc15th.pluv.domain.model.PlayListApp
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(oauth: PlayListApp, idToken: String): Flow<ApiResult<JwtToken>>
}