package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.History
import com.cmc15th.pluv.core.model.HistoryDetail
import com.cmc15th.pluv.core.model.SocialAccount
import com.cmc15th.pluv.core.model.SourceMusic
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    fun unRegisterMember(): Flow<ApiResult<String>>

    fun getNickName(): Flow<ApiResult<String>>

    fun changeNickName(nickName: String): Flow<ApiResult<String>>

    fun getHistories(): Flow<ApiResult<List<History>>>

    fun getHistoryDetail(historyId: Long): Flow<ApiResult<HistoryDetail>>

    fun getTransferSucceedHistoryMusics(historyId: Long): Flow<ApiResult<List<SourceMusic>>>

    fun getTransferFailedHistoryMusics(historyId: Long): Flow<ApiResult<List<SourceMusic>>>

    fun getIntegratedSocialLoginType(): Flow<ApiResult<List<SocialAccount>>>

}