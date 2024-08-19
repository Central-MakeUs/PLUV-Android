package com.cmc15th.pluv.core.datastore

import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    suspend fun saveAccessToken(token: String)
    fun getAccessToken(): Flow<String>
}