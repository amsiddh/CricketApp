package com.cricketapp.domain.usecase

import com.cricketapp.domain.model.MatchInfoDomain
import com.cricketapp.domain.repository.MatchesRepository
import com.cricketapp.common.Result
import javax.inject.Inject

interface GetMatchesUseCase {
    suspend fun getCurrentMatches(): Result<List<MatchInfoDomain>>
}

class GetCurrentMatchesUseCaseImpl @Inject constructor(
    private val matchesRepository: MatchesRepository
): GetMatchesUseCase {
    override suspend fun getCurrentMatches(): Result<List<MatchInfoDomain>> {
        return matchesRepository.getCurrentMatches()
    }
}