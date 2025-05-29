package com.apiguave.feature_auth.register

import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.feature_auth.extensions.filterIndex
import com.apiguave.auth_ui.extensions.toGender
import com.apiguave.auth_ui.extensions.toOrientation
import com.apiguave.auth_ui.extensions.toProviderAccount
import com.apiguave.domain_auth.usecases.GetMaxBirthdateUseCase
import com.apiguave.core_ui.model.PictureState
import com.apiguave.feature_auth.orchestrators.SignUpUseCase
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
        _uiState.update { it.copy(dialogState = SignUpDialogState.Loading) }
        viewModelScope.launch {
            val name = _uiState.value.name.text
            val birthdate = _uiState.value.birthDate
            val bio = _uiState.value.bio.text
            val gender = _uiState.value.genderIndex.toGender()
            val orientation = _uiState.value.orientationIndex.toOrientation()
            val pictures = _uiState.value.pictures

            val account = activityResult.toProviderAccount()
            val result = signUpUseCase(account, name, birthdate, bio, gender, orientation, pictures.map { it.uri.toString() })

            result.fold({
                _uiState.update { it.copy(isUserSignedIn = true) }
            }, { e ->
                _uiState.update { it.copy(dialogState = SignUpDialogState.ErrorDialog) }
            })

        }
    }
}

@Immutable
sealed class SignUpDialogState {
    data object NoDialog: SignUpDialogState()
    data class DeleteConfirmationDialog(val index: Int): SignUpDialogState()
    data object ErrorDialog: SignUpDialogState()
    data object SelectPictureDialog: SignUpDialogState()
    data object Loading: SignUpDialogState()
}

@Immutable
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