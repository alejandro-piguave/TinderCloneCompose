package com.apiguave.tinderclonecompose.ui.signup

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.account.AuthRepository
import com.apiguave.tinderclonecompose.data.profile.repository.ProfileRepository
import com.apiguave.tinderclonecompose.data.profile.repository.CreateUserProfile
import com.apiguave.tinderclonecompose.data.picture.repository.DevicePicture
import com.apiguave.tinderclonecompose.ui.extension.filterIndex
import com.apiguave.tinderclonecompose.ui.extension.toProviderAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        SignUpUiState(
            isLoading = false,
            pictures = emptyList(),
            isUserSignedIn = false,
            errorMessage = null
        )
    )
    val uiState = _uiState.asStateFlow()

    fun removePictureAt(index: Int) {
        _uiState.update { it.copy(pictures = it.pictures.filterIndex(index)) }
    }

    fun addPicture(picture: DevicePicture) {
        _uiState.update { it.copy(pictures = it.pictures + picture) }
    }

    fun signUp(activityResult: ActivityResult, profile: CreateUserProfile) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val account = activityResult.toProviderAccount()
                authRepository.signIn(account)
                profileRepository.createProfile(profile)
                _uiState.update { it.copy(isUserSignedIn = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message)
                }
            }
        }
    }


}

data class SignUpUiState(
    val isLoading: Boolean,
    val pictures: List<DevicePicture>,
    val isUserSignedIn: Boolean,
    val errorMessage: String?
)