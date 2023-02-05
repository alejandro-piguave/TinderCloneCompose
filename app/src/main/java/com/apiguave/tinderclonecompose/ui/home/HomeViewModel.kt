package com.apiguave.tinderclonecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.*
import com.apiguave.tinderclonecompose.data.repository.FirebaseRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _newMatch = MutableSharedFlow<NewMatch>()
    val newMatch = _newMatch.asSharedFlow()

    init{ fetchProfiles() }

    fun swipeUser(profile: Profile, isLike: Boolean){
        viewModelScope.launch {
            try {
                val match = FirebaseRepository.swipeUser(profile, isLike)
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
                profiles.map { async { FirebaseRepository.createUserProfile(getRandomUserId(),  it) } }.awaitAll()
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
                val profileList = FirebaseRepository.getProfiles()
                _uiState.update { HomeUiState.Success(currentProfile = profileList.currentProfile, profileList = profileList.profiles) }
            }catch (e: Exception){
                _uiState.update { HomeUiState.Error(e.message)}
            }
        }
    }

}

sealed class HomeUiState{
    object Loading: HomeUiState()
    data class Success(
        val currentProfile: CurrentProfile,
        val profileList: List<Profile>
    ): HomeUiState()
    data class Error(val message: String?): HomeUiState()
}
