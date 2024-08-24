package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    fun unRegisterMember(): Flow<ApiResult<String>>
}