package com.cricketapp.matches.details

import com.cricketapp.domain.model.MatchInfoDomain

class MatchDetailsContract {
    // UI state representing the different states of the match listing screen
    sealed interface MatchDetailsUiState {
        data object Loading : MatchDetailsUiState
        data class Success(val matchInfo: MatchInfoDomain) :
            MatchDetailsUiState

        data class Failure(val exception: Exception) :
            MatchDetailsUiState
    }

    // User  events that trigger changes in the state
    sealed interface MatchDetailsUiIntent {
        data class LoadMatchDetails(val matchId: String) : MatchDetailsUiIntent
    }
}