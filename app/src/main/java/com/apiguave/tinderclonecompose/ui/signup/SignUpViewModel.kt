package com.apiguave.tinderclonecompose.ui.signup

import androidx.activity.result.ActivityResult
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.auth.AuthRepository
import com.apiguave.tinderclonecompose.data.profile.repository.ProfileRepository
import com.apiguave.tinderclonecompose.data.profile.repository.CreateUserProfile
import com.apiguave.tinderclonecompose.data.picture.repository.DevicePicture
import com.apiguave.tinderclonecompose.ui.components.dialogs.eighteenYearsAgo
import com.apiguave.tinderclonecompose.ui.extension.filterIndex
import com.apiguave.tinderclonecompose.ui.extension.toProviderAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class SignUpViewModel(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpViewState())
    val uiState = _uiState.asStateFlow()

    fun setBirthDate(birthDate: LocalDate) {
        _uiState.update { it.copy(birthDate = birthDate) }
    }

    fun setBio(bio: TextFieldValue) {
        _uiState.update { it.copy(bio = bio) }
    }

    fun setName(name: TextFieldValue) {
        _uiState.update { it.copy(name = name) }
    }

    fun setGenderIndex(genderIndex: Int) {
        _uiState.update { it.copy(genderIndex = genderIndex) }
    }

    fun setOrientationIndex(orientationIndex: Int) {
        _uiState.update { it.copy(orientationIndex = orientationIndex) }
    }

    fun closeDialog() {
        _uiState.update { it.copy(dialogState = SignUpDialogState.NoDialog) }
    }

    fun showConfirmDeletionDialog(index: Int) {
        _uiState.update { it.copy(dialogState = SignUpDialogState.DeleteConfirmationDialog(index)) }
    }

    fun showSelectPictureDialog() {
        _uiState.update { it.copy(dialogState = SignUpDialogState.SelectPictureDialog) }
    }

    fun removePictureAt(index: Int) {
        _uiState.update { it.copy(pictures = it.pictures.filterIndex(index)) }
    }

    fun addPicture(picture: DevicePicture) {
        _uiState.update { it.copy(pictures = it.pictures + picture) }
    }

    fun signUp(activityResult: ActivityResult, profile: CreateUserProfile) {
        viewModelScope.launch {
            _uiState.update { it.copy(dialogState = SignUpDialogState.Loading) }
            try {
                val account = activityResult.toProviderAccount()
                authRepository.signIn(account)
                profileRepository.createProfile(profile)
                _uiState.update { it.copy(isUserSignedIn = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(dialogState = SignUpDialogState.ErrorDialog(e.message ?: ""))
                }
            }
        }
    }
}

sealed class SignUpDialogState {
    object NoDialog: SignUpDialogState()
    data class DeleteConfirmationDialog(val index: Int): SignUpDialogState()
    data class ErrorDialog(val message: String): SignUpDialogState()
    object SelectPictureDialog: SignUpDialogState()
    object Loading: SignUpDialogState()
}

data class SignUpViewState(
    val name: TextFieldValue = TextFieldValue(),
    val birthDate: LocalDate = eighteenYearsAgo,
    val bio: TextFieldValue = TextFieldValue(),
    val genderIndex: Int = -1,
    val orientationIndex: Int = -1,
    val pictures: List<DevicePicture> = emptyList(),
    val isUserSignedIn: Boolean = false,
    val dialogState: SignUpDialogState = SignUpDialogState.NoDialog
)