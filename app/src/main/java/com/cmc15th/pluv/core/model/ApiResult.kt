package com.cmc15th.pluv.core.model

import java.io.IOException
import java.net.ConnectException

/**
 * 네트워크 응답을 래핑하는 인터페이스
 */
sealed interface ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>
    data class Failure(val code: Int, val error: String?) : ApiResult<Nothing>
    data class NetworkError(val exception: IOException) : ApiResult<Nothing>
    data class Unexpected(val t: Throwable?) : ApiResult<Nothing>

    fun <R : Any> map(transform: (T) -> R): ApiResult<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Failure -> this
            is NetworkError -> this
            is Unexpected -> this
        }
    }

    fun onSuccess(block: (T) -> Unit) {
        if (this is Success) block(data)
    }

    fun onFailure(block: (Int, String?) -> Unit) {
        if (this is Failure) block(code, error)
        if (this is NetworkError) {
            if (exception is ConnectException) block(600, "서버 에러가 발생하였습니다. 관리자에게 문의 바랍니다.")
            block(601, "네트워크 에러가 발생하였습니다. 인터넷 연결을 확인해주세요.")
        }
        if (this is Unexpected) block(500, t?.message)
    }
}