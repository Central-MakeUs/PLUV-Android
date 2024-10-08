package com.cmc15th.pluv.core.network.interceptor

import com.cmc15th.pluv.core.datastore.AuthDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authDataSource: AuthDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        //FIXME runBlocking 대체
        val accessToken = runBlocking {
            authDataSource.getAccessToken().first()
        }
        if (accessToken.isBlank()) return chain.proceed(request)

        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val TAG = "AuthInterceptor"
    }
}