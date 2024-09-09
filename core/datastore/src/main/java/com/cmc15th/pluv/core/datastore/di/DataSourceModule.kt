package com.cmc15th.pluv.core.datastore.di

import com.cmc15th.pluv.core.datastore.AuthDataSource
import com.cmc15th.pluv.core.datastore.AuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthDataStore(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource
}