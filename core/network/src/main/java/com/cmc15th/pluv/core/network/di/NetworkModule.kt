package com.cmc15th.pluv.core.network.di

import com.cmc15th.pluv.core.datastore.AuthDataSource
import com.cmc15th.pluv.core.network.BuildConfig
import com.cmc15th.pluv.core.network.adapter.ApiResultCallAdapter
import com.cmc15th.pluv.core.network.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

private const val MaxTimeout = 60_000L

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthenticatedClient

    @Singleton
    @Provides
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @BaseClient
    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(MaxTimeout, TimeUnit.MILLISECONDS)
            .writeTimeout(MaxTimeout, TimeUnit.MILLISECONDS)
            .build()

    @AuthenticatedClient
    @Singleton
    @Provides
    fun provideOkHttpClientWithInterceptor(
        authDataSource: AuthDataSource,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(authDataSource))
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(MaxTimeout, TimeUnit.MILLISECONDS)
            .writeTimeout(MaxTimeout, TimeUnit.MILLISECONDS)
            .build()

    @BaseClient
    @Singleton
    @Provides
    fun provideRetrofitClient(@BaseClient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.server_url)
            .client(okHttpClient)
            .addCallAdapterFactory(ApiResultCallAdapter.Factory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @AuthenticatedClient
    @Singleton
    @Provides
    fun provideRetrofitClientWithInterceptor(@AuthenticatedClient okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.server_url)
            .client(okHttpClient)
            .addCallAdapterFactory(ApiResultCallAdapter.Factory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}