package com.cmc15th.pluv.core.data.repository

import com.cmc15th.pluv.core.datastore.AuthDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override suspend fun saveAccessToken(token: String) {
        authDataSource.saveAccessToken(token)
    }

    override fun getAccessToken(): Flow<String> = authDataSource.getAccessToken()
}