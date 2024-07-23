package com.cmc15th.pluv.core.data.di

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
}