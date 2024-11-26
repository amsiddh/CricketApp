package com.cricketapp.data.repository

import com.cricketapp.data.remote.datasource.MatchesRemoteDataSource
import com.cricketapp.domain.model.MatchInfoDomain
import com.cricketapp.domain.repository.MatchesRepository
import com.cricketapp.common.Result
import com.cricketapp.data.BuildConfig
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(
    private val remoteDataSource: MatchesRemoteDataSource
): MatchesRepository {

    override suspend fun getCurrentMatches(): Result<List<MatchInfoDomain>> {
        return remoteDataSource.getCurrentMatches(apiKey = BuildConfig.API_KEY)
    }

    override suspend fun getMatchDetails(matchId: String): Result<MatchInfoDomain> {
        return remoteDataSource.getMatchDetails(apiKey = BuildConfig.API_KEY, matchId)
    }
}