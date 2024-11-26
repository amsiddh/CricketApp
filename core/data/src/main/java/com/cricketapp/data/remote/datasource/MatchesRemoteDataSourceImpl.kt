package com.cricketapp.data.remote.datasource

import android.util.Log
import com.cricketapp.common.Result
import com.cricketapp.data.mapper.toDomain
import com.cricketapp.data.mapper.toDomainList
import com.cricketapp.data.remote.api.MatchesApi
import com.cricketapp.domain.model.MatchInfoDomain
import javax.inject.Inject

class MatchesRemoteDataSourceImpl @Inject constructor(
    private val matchesApi: MatchesApi,
): BaseRemoteDataSource(), MatchesRemoteDataSource {
    override suspend fun getCurrentMatches(apiKey: String): Result<List<MatchInfoDomain>> {
        return safeApiCall {
            matchesApi.getCurrentMatches(apiKey)
        }.let { result ->
            when (result) {
                is Result.Success -> {
                    if ("failure".equals(result.data.status, ignoreCase = true)) {
                        getResultError(result.data.reason)
                    } else {
                        Result.Success(result.data.data.toDomainList())
                    }
                }

                is Result.Error -> result
                is Result.Loading -> result
            }
        }
    }

    override suspend fun getMatchDetails(apiKey: String, matchId: String): Result<MatchInfoDomain> {
        return safeApiCall {
            matchesApi.getMatchDetails(apiKey, matchId)
        }.let { result ->
            when (result) {
                is Result.Success -> {
                    if ("failure".equals(result.data.status, ignoreCase = true)) {
                        getResultError(result.data.reason)
                    } else {
                        Result.Success(result.data.data.toDomain())
                    }
                }

                is Result.Error -> result
                is Result.Loading -> result
            }
        }
    }
}

private fun getResultError(reason: String?): Result.Error {
    return Result.Error(
        Exception(
            Throwable(
                message = reason ?: "No Data"
            )
        )
    )
}