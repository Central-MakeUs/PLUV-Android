package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.data.mapper.toJwtToken
import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.model.JwtToken
import com.cmc15th.pluv.core.network.request.GoogleLoginRequest
import com.cmc15th.pluv.core.network.service.LoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRepository {

    override fun googleLogin(idToken: String): Flow<ApiResult<JwtToken>> = flow {
        emit(
            loginService.googleLogin(GoogleLoginRequest(idToken)).map { result ->
                result.data.toJwtToken()
            }
        )
    }.flowOn(Dispatchers.IO)
}