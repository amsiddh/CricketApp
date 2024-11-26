package com.cricketapp.data.remote.datasource

import com.cricketapp.common.Result
import com.cricketapp.domain.model.MatchInfoDomain

interface MatchesRemoteDataSource {
    suspend fun getCurrentMatches(apiKey: String): Result<List<MatchInfoDomain>>
    suspend fun getMatchDetails(apiKey: String, matchId: String): Result<MatchInfoDomain>
}