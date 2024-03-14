package com.apiguave.tinderclonecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.home.HomeRepository
import com.apiguave.tinderclonecompose.data.profile.repository.ProfileRepository
import com.apiguave.tinderclonecompose.data.profile.repository.CreateUserProfile
import com.apiguave.tinderclonecompose.data.home.entity.NewMatch
import com.apiguave.tinderclonecompose.data.home.entity.Profile
import com.apiguave.tinderclonecompose.ui.extension.getRandomUserId
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val profileRepository: ProfileRepository,
    private val homeRepository: HomeRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _newMatch = MutableSharedFlow<NewMatch>()
    val newMatch = _newMatch.asSharedFlow()

    init{ fetchProfiles() }

    fun swipeUser(profile: Profile, isLike: Boolean){
        viewModelScope.launch {
            try {
                if(isLike) {
                    val match = homeRepository.likeProfile(profile)
                    if(match != null){
                        _newMatch.emit(match)
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
                profiles.map { async { profileRepository.createProfile(getRandomUserId(),  it) } }.awaitAll()
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
                val profiles = homeRepository.getProfiles()
                _uiState.update { HomeUiState.Success(profileList = profiles) }
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
