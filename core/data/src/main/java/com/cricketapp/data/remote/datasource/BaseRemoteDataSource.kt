package com.cricketapp.data.remote.datasource

import retrofit2.Response
import java.io.IOException

import com.cricketapp.common.Result

private const val ERROR_MESSAGE = "Unable to fetch the data, Please try again"

open class BaseRemoteDataSource {
    protected suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Result<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(Exception(ERROR_MESSAGE))
            } else {
                Result.Error(
                    Exception(
                        response.code().toString(),
                        Throwable(response.errorBody().toString())
                    )
                )
            }
        } catch (exception: Throwable) {
            when (exception) {
                is IOException -> Result.Error(exception)
                else -> Result.Error(Exception(ERROR_MESSAGE))
            }
        }
    }
}