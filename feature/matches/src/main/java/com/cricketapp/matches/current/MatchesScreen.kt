package com.cricketapp.matches.current

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cricketapp.domain.model.MatchInfoDomain
import com.cricketapp.domain.model.ScoreDomain
import com.cricketapp.domain.model.TeamInfoDomain
import com.cricketapp.matches.components.ErrorView
import com.cricketapp.matches.components.ProgressIndicator
import com.cricketapp.matches.components.TeamNameImage
import com.cricketapp.matches.components.TeamStatusText

@Composable
fun MatchesScreen(
    modifier: Modifier = Modifier,
    onMatchClick: (String, String) -> Unit
) {

    val viewModel: MatchesViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()

    val sideEffect by viewModel.sideEffect.collectAsStateWithLifecycle(initialValue = null)

    sideEffect?.let {
        when (it) {
            is MatchesContract.MatchesSideEffect.NavigateToMatchDetailsScreen -> {
                onMatchClick(it.matchId, it.matchName)
            }
        }
    }

    when (uiState) {
        is MatchesContract.MatchesUiState.Loading -> {
            ProgressIndicator(modifier = modifier)
        }

        is MatchesContract.MatchesUiState.Success -> {
            val matches = (uiState as MatchesContract.MatchesUiState.Success).matchList
            MatchListView(matches = matches,
                modifier = modifier,
                onMatchClick = { id, name ->
                    viewModel.onUiIntent(MatchesContract.MatchesUiIntent.OnMatchClicked(id, name))
                })
        }

        is MatchesContract.MatchesUiState.Failure -> {
            val errorMessage = (uiState as MatchesContract.MatchesUiState.Failure).exception.message
            ErrorView(errorMessage ?: "An error occurred", modifier = modifier)
        }
    }
}

@Composable
fun MatchListView(
    matches: List<MatchInfoDomain>,
    onMatchClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(matches) { match ->
            MatchItem(match, onMatchClick, modifier)
        }
    }
}

@Composable
fun MatchItem(
    match: MatchInfoDomain,
    onMatchClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        val detailTitle =
            if (match.teamInfo.isNotEmpty() && match.teamInfo.size > 1)
                "${match.teamInfo[0].shortname} vs ${match.teamInfo[1].shortname}"
            else
                match.name
        Column(
            modifier = Modifier
                .clickable { onMatchClick(match.id, detailTitle) }
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Venue: ${match.venue}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(0.7.toFloat()),
                )
                TeamStatusText(
                    match = match,
                    isDetails = false,
                )
            }
            TeamInfo(
                teamName = match.teams.first(),
                team = if (match.teamInfo.isNotEmpty()) match.teamInfo.first() else null,
                score = if (match.score.isNotEmpty()) match.score.first() else null,
                matchStarted = match.matchStarted,
                modifier = modifier,
            )
            TeamInfo(
                teamName = match.teams[1],
                team = if (match.teamInfo.size > 1) match.teamInfo[1] else null,
                score = if (match.score.size > 1) match.score[1] else null,
                matchStarted = match.matchStarted,
                modifier = modifier,
            )
            Text(
                text = match.status,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
fun TeamInfo(
    teamName: String,
    team: TeamInfoDomain?,
    score: ScoreDomain?,
    matchStarted: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        TeamNameImage(
            name = team?.name ?: teamName,
            imageUrl = team?.img ?: "",
            isDetails = false,
            modifier = modifier,
        )
        if (matchStarted && score != null) {
            Text(
                text = "${score.r}-${score.w}(${score.o})",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
