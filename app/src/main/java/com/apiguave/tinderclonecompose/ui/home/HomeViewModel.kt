package com.apiguave.tinderclonecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.domain.profilecard.ProfileCardRepository
import com.apiguave.tinderclonecompose.domain.profile.ProfileRepository
import com.apiguave.tinderclonecompose.domain.profile.entity.CreateUserProfile
import com.apiguave.tinderclonecompose.domain.profilecard.entity.CurrentProfile
import com.apiguave.tinderclonecompose.domain.profilecard.entity.NewMatch
import com.apiguave.tinderclonecompose.domain.profilecard.entity.Profile
import com.apiguave.tinderclonecompose.extensions.getRandomUserId
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val profileRepository: ProfileRepository,
    private val profileCardRepository: ProfileCardRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _currentProfile = MutableSharedFlow<CurrentProfile>()
    val currentProfile = _currentProfile.asSharedFlow()

    private val _newMatch = MutableSharedFlow<NewMatch>()
    val newMatch = _newMatch.asSharedFlow()

    init{ fetchProfiles() }

    fun swipeUser(profile: Profile, isLike: Boolean){
        viewModelScope.launch {
            try {
                val match = profileCardRepository.swipeUser(profile, isLike)
                if(match != null){
                    _newMatch.emit(match)
                }
            }catch (e: Exception){
                //Bringing the profile card back to the profile deck?
            }
        }
    }

    fun removeLastProfile(){
        _uiState.update {
            if(it is HomeUiState.Success){
                it.copy(profileList = it.profileList.dropLast(1))
            } else it
        }
    }

    fun setLoading(){
        _uiState.update { HomeUiState.Loading}
    }

    fun createProfiles(profiles: List<CreateUserProfile>){
        viewModelScope.launch {
            _uiState.update { HomeUiState.Loading }
            try {
                profiles.map { async { profileRepository.createUserProfile(getRandomUserId(),  it) } }.awaitAll()
                fetchProfiles()
            } catch (e: Exception) {
                _uiState.update { HomeUiState.Error(e.message) }
            }
        }
    }

    fun fetchProfiles(){
        viewModelScope.launch {
            _uiState.update { HomeUiState.Loading }
            try {
                val profileList = profileCardRepository.getProfiles()
                _uiState.update { HomeUiState.Success(profileList = profileList.profiles) }
                _currentProfile.emit(profileList.currentProfile)
            }catch (e: Exception){
                _uiState.update { HomeUiState.Error(e.message)}
            }
        }
    }

}

sealed class HomeUiState{
    object Loading: HomeUiState()
    data class Success(
        val profileList: List<Profile>
    ): HomeUiState()
    data class Error(val message: String?): HomeUiState()
}
