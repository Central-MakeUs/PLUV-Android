package com.cmc15th.pluv.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authDataStore: DataStore<Preferences>
) : AuthDataSource {

    override suspend fun saveAccessToken(token: String) {
        authDataStore.edit { settings ->
            settings[ACCESS_TOKEN] = token
        }
    }

    override fun getAccessToken(): Flow<String> =
        authDataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN] ?: ""
        }.catch {
            emit("")
        }


    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
    }
}