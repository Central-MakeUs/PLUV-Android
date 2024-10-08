package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.data.mapper.toHistory
import com.cmc15th.pluv.core.data.mapper.toHistoryDetail
import com.cmc15th.pluv.core.data.mapper.toSocialAccount
import com.cmc15th.pluv.core.data.mapper.toSourceMusic
import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.History
import com.cmc15th.pluv.core.model.HistoryDetail
import com.cmc15th.pluv.core.model.SocialAccount
import com.cmc15th.pluv.core.model.SourceMusic
import com.cmc15th.pluv.core.network.request.ChangeNickNameRequest
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

    override fun getNickName(): Flow<ApiResult<String>> = flow {
        emit(memberService.getNickName().map {
            it.data
        })
    }.flowOn(Dispatchers.IO)

    override fun changeNickName(nickName: String): Flow<ApiResult<String>> = flow {
        emit(memberService.changeNickName(ChangeNickNameRequest(nickName)).map {
            it.data
        })
    }.flowOn(Dispatchers.IO)

    override fun getHistories(): Flow<ApiResult<List<History>>> = flow {
        emit(memberService.getHistories().map { result ->
            result.data.map { it.toHistory() }
        })
    }.flowOn(Dispatchers.IO)

    override fun getHistoryDetail(historyId: Long): Flow<ApiResult<HistoryDetail>> = flow {
        emit(memberService.getHistoryDetail(historyId).map {
            it.data.toHistoryDetail()
        })
    }.flowOn(Dispatchers.IO)

    override fun getTransferSucceedHistoryMusics(historyId: Long): Flow<ApiResult<List<SourceMusic>>> =
        flow {
            emit(memberService.getTransferSucceedHistoryMusics(historyId).map { result ->
                result.data.map { it.toSourceMusic() }
            })
        }.flowOn(Dispatchers.IO)

    override fun getTransferFailedHistoryMusics(historyId: Long): Flow<ApiResult<List<SourceMusic>>> =
        flow {
            emit(memberService.getTransferFailedHistoryMusics(historyId).map { result ->
                result.data.map { it.toSourceMusic() }
            })
        }.flowOn(Dispatchers.IO)

    override fun getIntegratedSocialLoginType(): Flow<ApiResult<List<SocialAccount>>> = flow {
        emit(memberService.getIntegratedSocialLoginType().map {
            it.data.map { type -> type.toSocialAccount() }
        })
    }
}