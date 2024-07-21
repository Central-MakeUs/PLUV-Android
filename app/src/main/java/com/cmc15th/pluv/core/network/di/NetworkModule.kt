package com.cmc15th.pluv.core.network.di

import com.cmc15th.pluv.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val MaxTimeout = 15_000L
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(MaxTimeout, TimeUnit.MILLISECONDS)
            .writeTimeout(MaxTimeout, TimeUnit.MILLISECONDS)
            .build()

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.server_url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}