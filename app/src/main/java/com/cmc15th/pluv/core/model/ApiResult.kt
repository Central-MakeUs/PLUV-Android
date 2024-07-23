package com.cmc15th.pluv.core.model

import java.io.IOException

/**
 * 네트워크 응답을 래핑하는 인터페이스
 */
sealed interface ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T?) : ApiResult<T>
    data class Failure(val code: Int, val error: String?) : ApiResult<Nothing>
    data class NetworkError(val exception: IOException) : ApiResult<Nothing>
    data class Unexpected(val t: Throwable?) : ApiResult<Nothing>

    fun <R : Any> map(transform: (T?) -> R?): ApiResult<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Failure -> this
            is NetworkError -> this
            is Unexpected -> this
        }
    }
}