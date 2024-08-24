package com.cmc15th.pluv.core.data.di

import com.cmc15th.pluv.core.data.repository.AuthRepository
import com.cmc15th.pluv.core.data.repository.AuthRepositoryImpl
import com.cmc15th.pluv.core.data.repository.FeedRepository
import com.cmc15th.pluv.core.data.repository.FeedRepositoryImpl
import com.cmc15th.pluv.core.data.repository.LoginRepository
import com.cmc15th.pluv.core.data.repository.LoginRepositoryImpl
import com.cmc15th.pluv.core.data.repository.MemberRepository
import com.cmc15th.pluv.core.data.repository.MemberRepositoryImpl
import com.cmc15th.pluv.core.data.repository.PlaylistRepository
import com.cmc15th.pluv.core.data.repository.PlaylistRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlaylistRepository(
        playlistRepositoryImpl: PlaylistRepositoryImpl
    ): PlaylistRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindFeedRepository(
        feedRepositoryImpl: FeedRepositoryImpl
    ): FeedRepository

    @Binds
    @Singleton
    abstract fun bindMemberRepository(
        memberRepositoryImpl: MemberRepositoryImpl
    ): MemberRepository
}