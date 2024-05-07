package com.apiguave.tinderclonecompose.login

import androidx.activity.result.ActivityResult
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.extension.toProviderAccount
import com.apiguave.tinderclonedomain.usecase.IsUserSignedInUseCase
import com.apiguave.tinderclonedomain.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val isUserSignedInUseCase: IsUserSignedInUseCase,
    private val signInUseCase: SignInUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(
        LoginViewState(
            isLoading = true,
            isUserSignedIn = false,
            errorMessage = null
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        _uiState.update {
            if(isUserSignedInUseCase()){
                it.copy(isUserSignedIn = true)
            } else {
                it.copy(isLoading = false)
            }
        }
    }

    fun signIn(activityResult: ActivityResult) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        val account = activityResult.toProviderAccount()
        signInUseCase(account).fold({
            _uiState.update { it.copy(isUserSignedIn = true) }
        }, { e ->
            _uiState.update {
                it.copy(isLoading = false, errorMessage = e.message)
            }
        })
    }

}

@Immutable
data class LoginViewState(val isLoading: Boolean, val isUserSignedIn: Boolean, val errorMessage: String?)