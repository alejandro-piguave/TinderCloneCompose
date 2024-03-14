package com.apiguave.tinderclonecompose.ui.login

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.account.AccountRepository
import com.apiguave.tinderclonecompose.ui.extension.toProviderAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val accountRepository: AccountRepository): ViewModel() {
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
            if(accountRepository.isUserSignedIn){
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
                val account = activityResult.toProviderAccount()
                accountRepository.signIn(account)
                _uiState.update { it.copy(isUserSignedIn = true) }
            } catch (e: Exception) {
                if(accountRepository.isUserSignedIn){
                    accountRepository.signOut()
                }
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message)
                }
            }
        }
    }
}

data class LoginViewState(val isLoading: Boolean, val isUserSignedIn: Boolean, val errorMessage: String?)