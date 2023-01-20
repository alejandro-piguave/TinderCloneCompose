package com.apiguave.tinderclonecompose.ui.login

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState(
        isLoading = true,
        isUserSignedIn = false,
        errorMessage = null
    ))
    val uiState = _uiState.asStateFlow()

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        _uiState.update {
            val isUserSignedIn = FirebaseAuth.getInstance().currentUser != null
            if(isUserSignedIn){
                it.copy(isUserSignedIn = true)
            } else {
                it.copy(isLoading = false)
            }
        }
    }

    fun handleGoogleSignInActivityResult(activityResult: ActivityResult){
        _uiState.update {
            it.copy(isLoading = true)
        }
        val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            _uiState.update {
                it.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _uiState.update {
                        it.copy(isUserSignedIn = true)
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = task.exception?.message)
                    }
                }
            }
    }
}

data class LoginUiState(val isLoading: Boolean, val isUserSignedIn: Boolean, val errorMessage: String?)