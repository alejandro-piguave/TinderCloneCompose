package com.apiguave.tinderclonecompose.signup

import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.extension.filterIndex
import com.apiguave.tinderclonecompose.extension.toGender
import com.apiguave.tinderclonecompose.extension.toOrientation
import com.apiguave.tinderclonecompose.extension.toProviderAccount
import com.apiguave.tinderclonecompose.model.PictureState
import com.apiguave.tinderclonedomain.usecase.GetMaxBirthdateUseCase
import com.apiguave.tinderclonedomain.usecase.SignUpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class SignUpViewModel(
    getMaxBirthdateUseCase: GetMaxBirthdateUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpViewState())
    val uiState = _uiState.asStateFlow()

    init {
        getMaxBirthdateUseCase().let { max ->
            _uiState.update { it.copy(maxBirthDate = max, birthDate = max) }
        }
    }

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

    fun addPicture(picture: Uri) {
        _uiState.update { it.copy(pictures = it.pictures + PictureState.Local(picture)) }
    }

    fun signUp(activityResult: ActivityResult) {
        viewModelScope.launch {
            _uiState.update { it.copy(dialogState = SignUpDialogState.Loading) }
            try {
                val name = _uiState.value.name.text
                val birthdate = _uiState.value.birthDate
                val bio = _uiState.value.bio.text
                val gender = _uiState.value.genderIndex.toGender()
                val orientation = _uiState.value.orientationIndex.toOrientation()
                val pictures = _uiState.value.pictures

                val account = activityResult.toProviderAccount()
                signUpUseCase(account, name, birthdate, bio, gender, orientation, pictures.map { it.uri.toString() })

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
    val maxBirthDate: LocalDate = LocalDate.now(),
    val birthDate: LocalDate = LocalDate.now(),
    val bio: TextFieldValue = TextFieldValue(),
    val genderIndex: Int = -1,
    val orientationIndex: Int = -1,
    val pictures: List<PictureState.Local> = emptyList(),
    val isUserSignedIn: Boolean = false,
    val dialogState: SignUpDialogState = SignUpDialogState.NoDialog
)