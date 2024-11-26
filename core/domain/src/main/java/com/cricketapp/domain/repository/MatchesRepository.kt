package com.cricketapp.domain.repository

import com.cricketapp.domain.model.MatchInfoDomain
import com.cricketapp.common.Result

interface MatchesRepository {
    suspend fun getCurrentMatches(): Result<List<MatchInfoDomain>>
    suspend fun getMatchDetails(matchId: String): Result<MatchInfoDomain>
}