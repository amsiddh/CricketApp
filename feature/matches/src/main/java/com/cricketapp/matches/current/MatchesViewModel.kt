package com.cricketapp.matches.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cricketapp.common.Dispatcher
import com.cricketapp.common.Dispatchers.IO
import com.cricketapp.common.Result
import com.cricketapp.domain.model.MatchInfoDomain
import com.cricketapp.domain.usecase.GetMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    @Dispatcher(IO) private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    init {
        onUiIntent(MatchesContract.MatchesUiIntent.LoadMatches)
    }

    private val _uiState = MutableStateFlow<MatchesContract.MatchesUiState>(MatchesContract.MatchesUiState.Loading)
    val uiState: StateFlow<MatchesContract.MatchesUiState> = _uiState.asStateFlow()

    private val _sideEffect = Channel<MatchesContract.MatchesSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onUiIntent(intent: MatchesContract.MatchesUiIntent) {
        when (intent) {
            is MatchesContract.MatchesUiIntent.LoadMatches -> loadMatches()
            is MatchesContract.MatchesUiIntent.OnMatchClicked -> navigateToMatchDetails(intent)
        }
    }

    private fun loadMatches() {
        viewModelScope.launch(coroutineDispatcher) {
            try {
                when (val result = getMatchesUseCase.getCurrentMatches()) {
                    is Result.Loading -> _uiState.emit(MatchesContract.MatchesUiState.Loading)
                    is Result.Success -> _uiState.emit(MatchesContract.MatchesUiState.Success(sortMatches(result.data)))
                    is Result.Error -> _uiState.emit(MatchesContract.MatchesUiState.Failure(result.exception))
                }
            } catch (e: Exception) {
                _uiState.emit(MatchesContract.MatchesUiState.Failure(e))
            }
        }
    }

    private fun navigateToMatchDetails(intent: MatchesContract.MatchesUiIntent.OnMatchClicked) {
        _sideEffect.trySend(
            MatchesContract.MatchesSideEffect.NavigateToMatchDetailsScreen(
                matchId = intent.matchId,
                matchName = intent.matchName
            )
        )
    }

    private fun sortMatches(matches: List<MatchInfoDomain>): List<MatchInfoDomain> {
        return matches.sortedWith { match1, match2 ->
            val priority1 = if (match1.teamInfo.isNotEmpty() && match1.matchStarted) 0 else 1
            val priority2 = if (match2.teamInfo.isNotEmpty() && match2.matchStarted) 0 else 1
            if (priority1 != priority2) {
                priority1.compareTo(priority2)
            } else {
                match1.date.compareTo(match2.date)
            }
        }
    }
}
