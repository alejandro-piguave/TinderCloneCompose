package com.apiguave.tinderclonecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.Profile
import com.apiguave.tinderclonecompose.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeUiState(
            isLoading = true,
            profileList = emptyList(),
            errorMessage = null
        )
    )
    val uiState = _uiState.asStateFlow()

    init{
        fetchProfiles()
    }

    fun removeLastProfile(){
        _uiState.update { it.copy(profileList = it.profileList.dropLast(1)) }
    }

    fun fetchProfiles(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val profileList = FirestoreRepository.getProfiles()
                _uiState.update { it.copy(isLoading = false, profileList = profileList) }
            }catch (e: Exception){
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

}

data class HomeUiState(
    val isLoading: Boolean,
    val profileList: List<Profile>,
    val errorMessage: String?)