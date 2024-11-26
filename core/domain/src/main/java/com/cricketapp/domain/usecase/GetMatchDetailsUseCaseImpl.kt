package com.cricketapp.domain.usecase

import com.cricketapp.domain.model.MatchInfoDomain
import com.cricketapp.domain.repository.MatchesRepository
import com.cricketapp.common.Result
import javax.inject.Inject

interface GetMatchDetailsUseCase {
    suspend fun getMatchDetails(matchId: String): Result<MatchInfoDomain>
}

class GetMatchDetailsUseCaseImpl @Inject constructor(
    private val matchesRepository: MatchesRepository
): GetMatchDetailsUseCase {
    override suspend fun getMatchDetails(matchId: String): Result<MatchInfoDomain> {
        return matchesRepository.getMatchDetails(matchId)
    }
}