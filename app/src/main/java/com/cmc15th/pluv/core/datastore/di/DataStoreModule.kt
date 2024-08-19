package com.cmc15th.pluv.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * data store module
 */

private const val AUTH_DATASTORE = "auth_datastore"
private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = AUTH_DATASTORE)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Binds
    @Singleton
    fun bindAuthDataStore(@ApplicationContext context: Context) = context.authDataStore
}