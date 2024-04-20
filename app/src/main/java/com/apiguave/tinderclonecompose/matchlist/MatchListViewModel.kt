package com.apiguave.tinderclonecompose.matchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonedomain.match.Match
import com.apiguave.tinderclonedomain.usecase.GetMatchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatchListViewModel(private val getMatchesUseCase: GetMatchesUseCase): ViewModel() {
    private val _uiState = MutableStateFlow<MatchListViewState>(MatchListViewState.Loading)
    val uiState = _uiState.asStateFlow()

    init { fetchMatches() }
    fun fetchMatches() = viewModelScope.launch {
        _uiState.update { MatchListViewState.Loading }
        getMatchesUseCase().fold({ matches ->
            _uiState.update { MatchListViewState.Success(matches)}
        }, { e ->
            _uiState.update { MatchListViewState.Error(e.message ?: "") }
        })
    }

}


sealed class MatchListViewState {
    object Loading: MatchListViewState()
    data class Success(val matches: List<Match>): MatchListViewState()
    data class Error(val message: String): MatchListViewState()
}