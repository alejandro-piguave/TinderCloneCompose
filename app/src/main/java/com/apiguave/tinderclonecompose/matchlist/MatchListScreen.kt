package com.apiguave.tinderclonecompose.matchlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.apiguave.tinderclonecompose.model.MatchState
import org.koin.androidx.compose.koinViewModel

@Composable
fun MatchListScreen(
    onArrowBackPressed: () -> Unit,
    navigateToMatch: (MatchState) -> Unit,
    viewModel: MatchListViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    MatchListView(
        uiState = uiState,
        navigateToMatch = navigateToMatch,
        onArrowBackPressed = onArrowBackPressed,
        fetchMatches = viewModel::fetchMatches
    )
}