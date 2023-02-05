package com.apiguave.tinderclonecompose.ui.editprofile

import androidx.lifecycle.ViewModel
import com.apiguave.tinderclonecompose.data.CurrentProfile
import com.apiguave.tinderclonecompose.data.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditProfileViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        EditProfileUiState(
            currentProfile = null,
            isUserSignedOut = false
        )
    )
    val uiState = _uiState.asStateFlow()

    fun removePictureAt(index: Int){
        _uiState.update {
            it.copy(
                currentProfile = it.currentProfile?.copy(
                    pictures = it.currentProfile.pictures.filterIndexed{ itemIndex, _ ->
                        itemIndex != index
                    }
                )
            )
        }
    }

    fun setCurrentProfile(currentProfile: CurrentProfile){
        _uiState.update { it.copy(currentProfile = currentProfile) }
    }

    fun signOut(signInClient: GoogleSignInClient){
        AuthRepository.signOut()
        signInClient.signOut()
        _uiState.update { it.copy(isUserSignedOut = true) }
    }

}

data class EditProfileUiState(
    val currentProfile: CurrentProfile?,
    val isUserSignedOut: Boolean)