package com.cmc15th.pluv.core.network.adapter

import com.cmc15th.pluv.core.model.ApiResult
import com.cmc15th.pluv.core.network.response.CommonResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException

class ApiResultCall<T : Any>(private val delegate: Call<T>) : Call<ApiResult<T>> {

    override fun clone(): Call<ApiResult<T>> = ApiResultCall(delegate.clone())

    // execute() 함수는 사용하지 않기 때문에 UnsupportedOperationException을 던진다.
    override fun execute(): Response<ApiResult<T>> {
        throw UnsupportedOperationException("알 수 없는 에러")
    }

    override fun enqueue(callback: Callback<ApiResult<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val body = response.body()

                    val apiResult = if (body !is CommonResponse<*> || body.data == null) {
                        ApiResult.Failure(response.code(), "Invalid response body")
                    } else {
                        ApiResult.Success(body)
                    }

                    callback.onResponse(this@ApiResultCall, Response.success(apiResult))
                } else {
                    callback.onResponse(
                        this@ApiResultCall,
                        Response.success(
                            ApiResult.Failure(
                                response.code(),
                                response.errorBody()?.string()
                            )
                        )
                    )
                }
            }

            /**
             * 서버에서 Failure 또한 통신에 성공하여 onResponse로 처리되기 때문에 onFailure인 경우는 네트워크 에러
             */

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResponse = when (t) {
                    is ConnectException -> ApiResult.NetworkError(t)
                    is IOException -> ApiResult.NetworkError(t)
                    else -> ApiResult.Unexpected(t)
                }
                callback.onResponse(this@ApiResultCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}