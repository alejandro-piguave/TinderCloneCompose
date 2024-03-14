package com.apiguave.tinderclonecompose.ui.matchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.match.MatchRepository
import com.apiguave.tinderclonecompose.data.match.entity.Match
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatchListViewModel(private val matchRepository: MatchRepository): ViewModel() {
    private val _uiState = MutableStateFlow(MatchListUiState(true, emptyList(), null))
    val uiState = _uiState.asStateFlow()

    init { fetchMatches() }
    fun fetchMatches(){
        _uiState.update { it.copy(isLoading = false, errorMessage = null) }
        viewModelScope.launch {
            try {
                val matchList = matchRepository.getMatches()
                _uiState.update { it.copy(isLoading = false, matchList = matchList) }
            }catch (e: Exception){
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }


}
data class MatchListUiState(val isLoading: Boolean, val matchList: List<Match>, val errorMessage: String?)