package com.apiguave.tinderclonecompose.matchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonedomain.match.Match
import com.apiguave.tinderclonedomain.match.MatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatchListViewModel(private val matchRepository: MatchRepository): ViewModel() {
    private val _uiState = MutableStateFlow<MatchListViewState>(MatchListViewState.Loading)
    val uiState = _uiState.asStateFlow()

    init { fetchMatches() }
    fun fetchMatches(){
        _uiState.update { MatchListViewState.Loading }
        viewModelScope.launch {
            try {
                val matchList = matchRepository.getMatches()
                _uiState.update { MatchListViewState.Success(matchList)}
            }catch (e: Exception){
                _uiState.update { MatchListViewState.Error(e.message ?: "") }
            }
        }
    }
}


sealed class MatchListViewState {
    object Loading: MatchListViewState()
    data class Success(val matches: List<Match>): MatchListViewState()
    data class Error(val message: String): MatchListViewState()
}