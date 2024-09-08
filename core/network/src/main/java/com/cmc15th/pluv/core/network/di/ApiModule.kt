package com.cmc15th.pluv.core.network.di

import com.cmc15th.pluv.core.network.service.FeedService
import com.cmc15th.pluv.core.network.service.LoginService
import com.cmc15th.pluv.core.network.service.MemberService
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
    fun provideMigrationService(@NetworkModule.AuthenticatedClient retrofit: Retrofit): MigrationService =
        retrofit.create(MigrationService::class.java)

    @Provides
    @Singleton
    fun provideLoginService(@NetworkModule.BaseClient retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Provides
    @Singleton
    fun provideFeedService(@NetworkModule.AuthenticatedClient retrofit: Retrofit): FeedService =
        retrofit.create(FeedService::class.java)

    @Provides
    @Singleton
    fun provideMemberService(@NetworkModule.AuthenticatedClient retrofit: Retrofit): MemberService =
        retrofit.create(MemberService::class.java)

}