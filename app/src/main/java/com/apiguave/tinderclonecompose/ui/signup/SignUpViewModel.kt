package com.apiguave.tinderclonecompose.ui.signup

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.repository.AuthRepository
import com.apiguave.tinderclonecompose.data.CreateUserProfile
import com.apiguave.tinderclonecompose.data.repository.FirebaseRepository
import com.apiguave.tinderclonecompose.data.repository.SignInCheck
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        SignUpUiState(
            isLoading = false,
            isUserSignedIn = false,
            errorMessage = null
        )
    )
    val uiState = _uiState.asStateFlow()
    fun setLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading, errorMessage = null) }
    }

    fun signUp(data: Intent?, profile: CreateUserProfile) {
        viewModelScope.launch {
            try {
                AuthRepository.signInWithGoogle(data, signInCheck = SignInCheck.ENFORCE_NEW_USER)
                FirebaseRepository.createUserProfile(profile)
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
    val isUserSignedIn: Boolean,
    val errorMessage: String?
)