package com.cmc15th.pluv.core.data.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun saveAccessToken(token: String)
    fun getAccessToken(): Flow<String>
}