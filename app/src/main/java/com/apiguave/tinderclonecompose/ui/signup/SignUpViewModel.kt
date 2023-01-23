package com.apiguave.tinderclonecompose.ui.signup

import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.domain.Orientation
import com.apiguave.tinderclonecompose.repository.AuthRepository
import com.apiguave.tinderclonecompose.repository.FirestoreRepository
import com.apiguave.tinderclonecompose.repository.SignInCheck
import com.apiguave.tinderclonecompose.repository.StorageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

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

    fun signUp(
        data: Intent?,
        name: String,
        birthdate: LocalDate,
        bio: String,
        isMale: Boolean,
        orientation: Orientation,
        pictures: List<Bitmap>
    ) {
        viewModelScope.launch {
            try {
                AuthRepository.signInWithGoogle(data, signInCheck = SignInCheck.ENFORCE_NEW_USER)
                val filenames = StorageRepository.uploadUserPictures(pictures)
                FirestoreRepository.createUserProfile(name, birthdate, bio, isMale, orientation, filenames)

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