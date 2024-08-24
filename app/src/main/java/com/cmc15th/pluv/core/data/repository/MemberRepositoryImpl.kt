package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.network.service.MemberService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberService: MemberService
) : MemberRepository {

    override fun unRegisterMember(): Flow<ApiResult<String>> = flow {
        emit(memberService.unRegisterMember().map {
            it.data
        })
    }.flowOn(Dispatchers.IO)

}