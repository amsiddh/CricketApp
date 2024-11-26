package com.cricketapp.matches.current

import com.cricketapp.domain.model.MatchInfoDomain

sealed class MatchesContract {
    // UI state representing the different states of the match listing screen
    sealed interface MatchesUiState {
        data object Loading : MatchesUiState
        data class Success(val matchList: List<MatchInfoDomain>) :
            MatchesUiState

        data class Failure(val exception: Exception) :
            MatchesUiState
    }

    // User  events that trigger changes in the state
    sealed interface MatchesUiIntent {
        data object LoadMatches : MatchesUiIntent
        data class OnMatchClicked(val matchId: String, val matchName: String) :
            MatchesUiIntent
    }

    // Side effects that are triggered as a result of events
    sealed interface MatchesSideEffect {
        data class NavigateToMatchDetailsScreen(val matchId: String, val matchName: String) :
            MatchesSideEffect
    }
}