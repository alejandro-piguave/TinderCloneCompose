package com.apiguave.tinderclonecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.home.HomeRepository
import com.apiguave.tinderclonecompose.data.profile.repository.ProfileRepository
import com.apiguave.tinderclonecompose.data.profile.repository.CreateUserProfile
import com.apiguave.tinderclonecompose.data.home.entity.NewMatch
import com.apiguave.tinderclonecompose.data.home.entity.Profile
import com.apiguave.tinderclonecompose.data.message.repository.MessageRepository
import com.apiguave.tinderclonecompose.ui.extension.getRandomUserId
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val profileRepository: ProfileRepository,
    private val homeRepository: HomeRepository,
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
                    val match = homeRepository.likeProfile(profile)
                    if(match != null){
                        _uiState.update { it.copy(dialogState = HomeViewDialogState.NewMatchDialog(match)) }
                    }
                } else {
                    homeRepository.passProfile(profile)
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

    fun createProfiles(profiles: List<CreateUserProfile>){
        viewModelScope.launch {
            _uiState.update { it.copy(contentState = HomeViewContentState.Loading) }
            try {
                profiles.map { async { profileRepository.createProfile(getRandomUserId(),  it) } }.awaitAll()
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
                val profiles = homeRepository.getProfiles()
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