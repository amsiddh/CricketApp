package com.cricketapp.matches.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cricketapp.domain.model.MatchInfoDomain
import com.cricketapp.domain.model.ScoreDomain
import com.cricketapp.domain.model.TeamInfoDomain
import com.cricketapp.matches.components.ErrorView
import com.cricketapp.matches.components.ProgressIndicator
import com.cricketapp.matches.components.TeamNameImage
import com.cricketapp.matches.components.TeamStatusText
import com.cricketapp.matches.current.MatchesContract

@Composable
fun MatchDetailsScreen(
    modifier: Modifier = Modifier,
) {

    val viewModel: MatchDetailsViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is MatchDetailsContract.MatchDetailsUiState.Loading -> {
            ProgressIndicator(modifier = modifier)
        }

        is MatchDetailsContract.MatchDetailsUiState.Success -> {
            val matchInfo = (uiState as MatchDetailsContract.MatchDetailsUiState.Success).matchInfo
            MatchInfoView(matchInfo, modifier)
        }

        is MatchDetailsContract.MatchDetailsUiState.Failure -> {
            val errorMessage = (uiState as MatchesContract.MatchesUiState.Failure).exception.message
            ErrorView(errorMessage ?: "An error occurred", modifier = modifier)
        }
    }
}

@Composable
fun MatchInfoView(
    matchInfo: MatchInfoDomain,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Text(
            text = matchInfo.name,
            style = MaterialTheme.typography.displaySmall,
        )
        Spacer(modifier = modifier.padding(vertical = 12.dp))
        TeamStatusText(
            match = matchInfo,
            isDetails = false,
        )
        Spacer(modifier = modifier.padding(vertical = 12.dp))
        val teamOneScores = matchInfo.score.filterIndexed { index, _ -> index % 2 == 0 }
        val teamTwoScores = matchInfo.score.filterIndexed { index, _ -> index % 2 != 0 }
        TeamInfo(
            teamName = matchInfo.teams.first(),
            team = if (matchInfo.teamInfo.isNotEmpty()) matchInfo.teamInfo.first() else null,
            scores = teamOneScores,
            modifier = modifier,
        )
        Spacer(modifier = modifier.padding(vertical = 16.dp))
        TeamInfo(
            teamName = matchInfo.teams[1],
            team = if (matchInfo.teamInfo.size > 1) matchInfo.teamInfo[1] else null,
            scores = teamTwoScores,
            modifier = modifier,
        )
        Spacer(modifier = modifier.padding(vertical = 12.dp))
        Text(
            text = matchInfo.status,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = modifier.padding(vertical = 12.dp))
        Text(
            text = matchInfo.venue,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = modifier.padding(vertical = 12.dp))
        Text(
            text = "The toss won by ${matchInfo.tossWinner} and choose to ${matchInfo.tossChoice}",
            style = MaterialTheme.typography.bodyMedium,
        )
    }

}

@Composable
fun TeamInfo(
    teamName: String,
    team: TeamInfoDomain?,
    scores: List<ScoreDomain>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TeamNameImage(
            name = team?.name ?: teamName,
            imageUrl = team?.img ?: "",
            isDetails = true,
            modifier = modifier,
        )
        if (scores.isNotEmpty()) {
            LazyColumn(modifier = modifier) {
                items(scores) { score ->
                    Text(
                        text = score.inning,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "${score.r}-${score.w}(${score.o})",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = modifier.padding(vertical = 6.dp))
                }
            }
        }
    }
}
