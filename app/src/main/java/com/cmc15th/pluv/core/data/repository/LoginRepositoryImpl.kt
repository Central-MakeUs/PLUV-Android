package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.data.mapper.toJwtToken
import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.JwtToken
import com.cmc15th.pluv.core.network.request.LoginRequest
import com.cmc15th.pluv.core.network.service.LoginService
import com.cmc15th.pluv.domain.model.PlayListApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRepository {

    override fun loginWithGoogle(
        oauth: PlayListApp,
        idToken: String
    ): Flow<ApiResult<JwtToken>> = flow {
        emit(
            loginService.pluvLogin(oauth, LoginRequest(idToken)).map { result ->
                result.data.toJwtToken()
            }
        )
    }.flowOn(Dispatchers.IO)
}