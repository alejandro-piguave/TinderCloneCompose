package com.apiguave.tinderclonecompose.ui.editprofile

import androidx.lifecycle.ViewModel
import com.apiguave.tinderclonecompose.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EditProfileViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(
        EditProfileUiState(
            isUserSignedOut = false
        )
    )
    val uiState = _uiState.asStateFlow()

    fun signOut(){
        AuthRepository.signOut()
        _uiState.update { it.copy(isUserSignedOut = true) }
    }

}

data class EditProfileUiState(val isUserSignedOut: Boolean)