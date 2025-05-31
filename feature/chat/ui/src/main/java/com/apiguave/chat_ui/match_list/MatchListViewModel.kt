package com.apiguave.chat_ui.match_list

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.core_ui.model.ProfilePictureState
import com.apiguave.match_domain.model.Match
import com.apiguave.match_domain.usecases.GetMatchesUseCase
import com.apiguave.picture_domain.usecases.GetPictureUseCase
import com.apiguave.chat_ui.model.MatchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatchListViewModel(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val getPictureUseCase: GetPictureUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<MatchListViewState>(MatchListViewState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchMatches()
    }

    fun fetchMatches() = viewModelScope.launch {
        _uiState.update { MatchListViewState.Loading }
        getMatchesUseCase().fold({ matches ->
            _uiState.update {
                MatchListViewState.Success(matches.map {
                    MatchState(
                        it,
                        ProfilePictureState.Loading(it.profile.pictureNames.first())
                    )
                })
            }
            matches.forEach { loadProfilePicture(it) }
        }, { e ->
            _uiState.update { MatchListViewState.Error(e.message ?: "") }
        })
    }

    private fun loadProfilePicture(match: Match) {
        viewModelScope.launch {
            getPictureUseCase(
                match.profile.id,
                match.profile.pictureNames.first()
            ).onSuccess { pictureUrl ->
                updateProfilePicture(match.id, Uri.parse(pictureUrl))
            }
        }
    }

    private fun updateProfilePicture(matchId: String, pictureUrl: Uri) {
        _uiState.update {
            if (it is MatchListViewState.Success) {
                it.copy(matches = it.matches.map { matchState ->
                    if (matchState.match.id == matchId) matchState.copy(
                        pictureState = ProfilePictureState.Remote(pictureUrl)
                    )
                    else matchState
                })
            } else it
        }
    }
}

@Immutable
sealed class MatchListViewState {
    object Loading : MatchListViewState()
    data class Success(val matches: List<MatchState>) : MatchListViewState()
    data class Error(val message: String) : MatchListViewState()
}