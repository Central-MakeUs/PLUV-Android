package com.cmc15th.pluv.core.network.di

import com.cmc15th.pluv.core.network.service.LoginService
import com.cmc15th.pluv.core.network.service.MigrationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideMigrationService(retrofit: Retrofit): MigrationService =
        retrofit.create(MigrationService::class.java)

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

}