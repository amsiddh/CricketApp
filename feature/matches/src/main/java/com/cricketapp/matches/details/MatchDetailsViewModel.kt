package com.cricketapp.matches.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cricketapp.common.Dispatcher
import com.cricketapp.common.Dispatchers.IO
import com.cricketapp.common.Result
import com.cricketapp.domain.usecase.GetMatchDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    private val getMatchDetailsUseCase: GetMatchDetailsUseCase,
    @Dispatcher(IO) private val coroutineDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    init {
        savedStateHandle.get<String>("matchId")?.let {
            onUiIntent(MatchDetailsContract.MatchDetailsUiIntent.LoadMatchDetails(it))
        }
    }

    private val _uiState = MutableStateFlow<MatchDetailsContract.MatchDetailsUiState>(MatchDetailsContract.MatchDetailsUiState.Loading)
    val uiState: StateFlow<MatchDetailsContract.MatchDetailsUiState> = _uiState.asStateFlow()

    private fun onUiIntent(intent: MatchDetailsContract.MatchDetailsUiIntent) {
        when (intent) {
            is MatchDetailsContract.MatchDetailsUiIntent.LoadMatchDetails -> loadMatchDetails(intent.matchId)
        }
    }

    private fun loadMatchDetails(matchId: String) {
        viewModelScope.launch(coroutineDispatcher) {
            try {
                when (val result = getMatchDetailsUseCase.getMatchDetails(matchId)) {
                    is Result.Loading -> _uiState.emit(MatchDetailsContract.MatchDetailsUiState.Loading)
                    is Result.Success -> _uiState.emit(MatchDetailsContract.MatchDetailsUiState.Success(result.data))
                    is Result.Error -> _uiState.emit(MatchDetailsContract.MatchDetailsUiState.Failure(result.exception))
                }
            } catch (e: Exception) {
                _uiState.emit(MatchDetailsContract.MatchDetailsUiState.Failure(e))
            }
        }
    }
}
