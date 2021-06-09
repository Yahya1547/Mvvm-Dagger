package com.future.mvvm_dagger.network


enum class ApiState {
    SUCCESS,
    NETWORK_ERROR,
    HTTP_ERROR,
    UNKNOWN_ERROR,
}

class ApiResult<T>(
    var state: ApiState,
    var data: T?,
    var errorMessage: String = "",
) {

    companion object {
        fun <T> success(data: T): ApiResult<T> {
            return ApiResult(ApiState.SUCCESS, data)
        }

        fun <T> failureNetwork(errorMessage: String): ApiResult<T> {
            return ApiResult(ApiState.NETWORK_ERROR, null, errorMessage)
        }

        fun <T> failureHttp(errorMessage: String): ApiResult<T> {
            return ApiResult(ApiState.HTTP_ERROR, null, errorMessage)
        }

        fun <T> failureUnknown(errorMessage: String): ApiResult<T> {
            return ApiResult(ApiState.UNKNOWN_ERROR, null, errorMessage)
        }
    }

}