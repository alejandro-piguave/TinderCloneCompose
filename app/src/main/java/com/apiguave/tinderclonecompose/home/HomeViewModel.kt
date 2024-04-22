package com.apiguave.tinderclonecompose.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonedomain.match.Match
import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.usecase.GenerateProfilesUseCase
import com.apiguave.tinderclonedomain.usecase.GetProfilesUseCase
import com.apiguave.tinderclonedomain.usecase.LikeProfileUseCase
import com.apiguave.tinderclonedomain.usecase.PassProfileUseCase
import com.apiguave.tinderclonedomain.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val generateProfilesUseCase: GenerateProfilesUseCase,
    private val getProfilesUseCase: GetProfilesUseCase,
    private val likeProfileUseCase: LikeProfileUseCase,
    private val passProfileUseCase: PassProfileUseCase,
    private val sendMessageUseCase: SendMessageUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeViewState(HomeViewDialogState.NoDialog, HomeViewContentState.Loading))
    val uiState = _uiState.asStateFlow()

    init { fetchProfiles() }

    fun showProfileGenerationDialog() {
        _uiState.update { it.copy(dialogState = HomeViewDialogState.GenerateProfilesDialog) }
    }

    fun closeDialog() {
        _uiState.update {
            it.copy(dialogState = HomeViewDialogState.NoDialog)
        }
    }

    fun sendMessage(matchId: String, text: String) = viewModelScope.launch {
        sendMessageUseCase(matchId, text)
    }


    fun swipeUser(profile: Profile, isLike: Boolean) = viewModelScope.launch {
        if (isLike) {
            likeProfileUseCase(profile).onSuccess {
                it?.let { newMatch ->
                    _uiState.update { it.copy(dialogState = HomeViewDialogState.NewMatchDialog(newMatch)) }
                }
            }
        } else {
            passProfileUseCase(profile)
        }
    }

    fun removeLastProfile(){
        _uiState.update {
            if(it.contentState is HomeViewContentState.Success){
                it.copy(contentState = it.contentState.copy(profileList = it.contentState.profileList.dropLast(1)))
            } else it
        }
    }

    fun setLoading(){
        _uiState.update { it.copy(contentState = HomeViewContentState.Loading) }
    }

    fun generateProfiles(amount: Int) = viewModelScope.launch {
        _uiState.update { it.copy(contentState = HomeViewContentState.Loading) }
        generateProfilesUseCase(amount).fold({
            fetchProfiles()
        }, {error ->
            _uiState.update { it.copy(contentState = HomeViewContentState.Error(error.message ?: "")) }
        })
    }


    fun fetchProfiles() = viewModelScope.launch {
        _uiState.update { it.copy(contentState = HomeViewContentState.Loading) }
        getProfilesUseCase().fold({profiles ->
            _uiState.update { it.copy(contentState = HomeViewContentState.Success(profileList = profiles)) }
        }, { error ->
            _uiState.update { it.copy(contentState = HomeViewContentState.Error(error.message ?: "")) }
        })
    }

}

data class HomeViewState(
    val dialogState: HomeViewDialogState,
    val contentState: HomeViewContentState
)

sealed class HomeViewDialogState {
    object NoDialog: HomeViewDialogState()
    object GenerateProfilesDialog: HomeViewDialogState()
    data class NewMatchDialog(val match: Match): HomeViewDialogState()
}

sealed class HomeViewContentState {
    object Loading: HomeViewContentState()
    data class Success(val profileList: List<Profile>): HomeViewContentState()
    data class Error(val message: String): HomeViewContentState()
}