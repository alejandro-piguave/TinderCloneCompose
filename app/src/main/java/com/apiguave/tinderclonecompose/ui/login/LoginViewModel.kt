package com.apiguave.tinderclonecompose.ui.login

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.datasource.SignInCheck
import com.apiguave.tinderclonecompose.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState(true, false, null))
    val uiState = _uiState.asStateFlow()

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        _uiState.update {
            if(AuthRepository.isUserSignedIn){
                it.copy(isUserSignedIn = true)
            } else {
                it.copy(isLoading = false)
            }
        }
    }

    fun signIn(activityResult: ActivityResult){
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                AuthRepository.signInWithGoogle(activityResult.data, signInCheck = SignInCheck.ENFORCE_EXISTING_USER)
                _uiState.update { it.copy(isUserSignedIn = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message)
                }
            }
        }
    }
}

data class LoginUiState(val isLoading: Boolean, val isUserSignedIn: Boolean, val errorMessage: String?)