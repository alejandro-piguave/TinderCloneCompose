package com.apiguave.feature_auth.login

import androidx.activity.result.ActivityResult
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.domain_auth.model.Account
import com.apiguave.feature_auth.extensions.toProviderAccount
import com.apiguave.domain_auth.usecases.SignInUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _eventChannel = Channel<LoginViewEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow<LoginViewState>(LoginViewState.Ready)
    val uiState = _uiState.asStateFlow()


    fun signIn(activityResult: ActivityResult) = viewModelScope.launch {
        val account: Account = try {
            activityResult.toProviderAccount()
        } catch (e: Exception) {
            _eventChannel.send(LoginViewEvent.LoginError)
            return@launch
        }

        _uiState.update { LoginViewState.Loading }
        signInUseCase(account).fold({
            _eventChannel.send(LoginViewEvent.NavigateHome)
        }, { _ ->
            _eventChannel.send(LoginViewEvent.LoginError)
        })

    }

}

@Immutable
sealed class LoginViewEvent {
    data object NavigateHome : LoginViewEvent()
    data object LoginError : LoginViewEvent()
}

@Immutable
sealed class LoginViewState {
    data object Loading : LoginViewState()
    data object Ready : LoginViewState()
}