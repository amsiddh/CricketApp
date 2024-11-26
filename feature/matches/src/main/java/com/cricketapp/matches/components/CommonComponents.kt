package com.cricketapp.matches.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.cricketapp.domain.model.MatchInfoDomain

@Composable
fun TeamStatusText(
    match: MatchInfoDomain,
    isDetails: Boolean,
) {
    val matchStatus =
        if (match.matchEnded) "Completed" else if (match.matchStarted) "Live" else "Upcoming"
    Text(
        text = "${match.matchType.uppercase()} | $matchStatus",
        style = if (isDetails) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium,
        textAlign = if (isDetails) TextAlign.Left else TextAlign.Right,
    )
}

@Composable
fun ProgressIndicator(
    modifier: Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ErrorView(errorMessage: String, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Error: $errorMessage",
            color = Color.Red,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
    }
}