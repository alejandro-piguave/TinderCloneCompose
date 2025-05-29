package com.apiguave.feature_auth.login

import androidx.activity.result.ActivityResult
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.auth_ui.extensions.toProviderAccount
import com.apiguave.domain_auth.usecases.IsUserSignedInUseCase
import com.apiguave.domain_auth.usecases.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val isUserSignedInUseCase: IsUserSignedInUseCase,
    private val signInUseCase: SignInUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<LoginViewState>(LoginViewState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        _uiState.update {
            if(isUserSignedInUseCase()){
                LoginViewState.SignedIn
            } else {
                LoginViewState.SigningIn
            }
        }
    }

    fun signIn(activityResult: ActivityResult) = viewModelScope.launch {
        _uiState.update { LoginViewState.Loading }
        val account = activityResult.toProviderAccount()
        signInUseCase(account).fold({
            _uiState.update { LoginViewState.SignedIn }
        }, { _ ->
            _uiState.update { LoginViewState.Error }
        })
    }

}

@Immutable
sealed class LoginViewState {
    data object Loading: LoginViewState()
    data object SigningIn: LoginViewState()
    data object SignedIn: LoginViewState()
    data object Error: LoginViewState()
}