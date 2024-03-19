package com.apiguave.tinderclonecompose.presentation.matchlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.apiguave.tinderclonecompose.data.match.repository.Match
import org.koin.androidx.compose.koinViewModel

@Composable
fun MatchListScreen(
    onArrowBackPressed: () -> Unit,
    navigateToMatch: (Match) -> Unit,
    viewModel: MatchListViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    MatchListView(
        uiState = uiState,
        navigateToMatch = navigateToMatch,
        onArrowBackPressed = onArrowBackPressed,
        fetchMatches = viewModel::fetchMatches
    )
}