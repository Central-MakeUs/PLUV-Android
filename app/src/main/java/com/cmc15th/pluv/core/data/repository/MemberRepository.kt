package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.History
import com.cmc15th.pluv.core.model.HistoryDetail
import com.cmc15th.pluv.core.model.SourceMusic
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    fun unRegisterMember(): Flow<ApiResult<String>>

    fun getNickName(): Flow<ApiResult<String>>

    fun changeNickName(nickName: String): Flow<ApiResult<String>>

    fun getHistories(): Flow<ApiResult<List<History>>>

    fun getHistoryDetail(historyId: Int): Flow<ApiResult<HistoryDetail>>

    fun getTransferSucceedHistoryMusics(historyId: Int): Flow<ApiResult<List<SourceMusic>>>

    fun getTransferFailedHistoryMusics(historyId: Int): Flow<ApiResult<List<SourceMusic>>>
}