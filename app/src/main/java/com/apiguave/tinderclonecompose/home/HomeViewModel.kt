package com.apiguave.tinderclonecompose.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonedomain.profile.NewMatch
import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.message.MessageRepository
import com.apiguave.tinderclonedomain.profile.ProfileGenerator
import com.apiguave.tinderclonedomain.profile.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val profileGenerator: ProfileGenerator,
    private val profileRepository: ProfileRepository,
    private val messageRepository: MessageRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeViewState(HomeViewDialogState.NoDialog, HomeViewContentState.Loading))
    val uiState = _uiState.asStateFlow()

    init{ fetchProfiles() }

    fun showProfileGenerationDialog() {
        _uiState.update { it.copy(dialogState = HomeViewDialogState.GenerateProfilesDialog) }
    }

    fun closeDialog() {
        _uiState.update {
            it.copy(dialogState = HomeViewDialogState.NoDialog)
        }
    }

    fun sendMessage(matchId: String, text: String){
        viewModelScope.launch {
            try {
                messageRepository.sendMessage(matchId, text)
            }catch (e: Exception){
                //Show the message as unsent?
            }
        }
    }

    fun swipeUser(profile: Profile, isLike: Boolean){
        viewModelScope.launch {
            try {
                if(isLike) {
                    val match = profileRepository.likeProfile(profile)
                    if(match != null){
                        _uiState.update { it.copy(dialogState = HomeViewDialogState.NewMatchDialog(match)) }
                    }
                } else {
                    profileRepository.passProfile(profile)
                }

            }catch (e: Exception){
                //Bringing the profile card back to the profile deck?
            }
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

    fun createProfiles(amount: Int){
        viewModelScope.launch {
            _uiState.update { it.copy(contentState = HomeViewContentState.Loading) }
            try {
                //TODO: move this logic to a use case
                val profiles = profileGenerator.generateProfiles(amount)
                profiles.forEach {
                    profileRepository.createProfile(it)
                }
                fetchProfiles()
            } catch (e: Exception) {
                _uiState.update { it.copy(contentState = HomeViewContentState.Error(e.message ?: "")) }
            }
        }
    }

    fun fetchProfiles(){
        viewModelScope.launch {
            _uiState.update { it.copy(contentState = HomeViewContentState.Loading) }
            try {
                val profiles = profileRepository.getProfiles()
                _uiState.update { it.copy(contentState = HomeViewContentState.Success(profileList = profiles)) }
            }catch (e: Exception){
                _uiState.update { it.copy(contentState = HomeViewContentState.Error(e.message ?: "")) }
            }
        }
    }

}

data class HomeViewState(
    val dialogState: HomeViewDialogState,
    val contentState: HomeViewContentState
)

sealed class HomeViewDialogState {
    object NoDialog: HomeViewDialogState()
    object GenerateProfilesDialog: HomeViewDialogState()
    data class NewMatchDialog(val newMatch: NewMatch): HomeViewDialogState()
}

sealed class HomeViewContentState {
    object Loading: HomeViewContentState()
    data class Success(val profileList: List<Profile>): HomeViewContentState()
    data class Error(val message: String): HomeViewContentState()
}