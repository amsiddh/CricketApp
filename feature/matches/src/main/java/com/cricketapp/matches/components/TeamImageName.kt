package com.cricketapp.matches.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cricketapp.matches.R

@Composable
fun TeamNameImage(
    name: String,
    imageUrl: String,
    isDetails: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = imageUrl,
            placeholder = painterResource(id = R.drawable.ic_team_placeholder),
            error = painterResource(id = R.drawable.ic_team_placeholder),
            contentDescription = null,
            modifier = modifier
                .size(40.dp)
                .padding(end = 12.dp)
        )
        Text(
            text = name,
            style = if (isDetails) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyLarge
        )
    }
}