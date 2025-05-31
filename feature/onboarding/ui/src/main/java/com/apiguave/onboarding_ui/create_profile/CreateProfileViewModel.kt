package com.apiguave.onboarding_ui.create_profile

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.core_ui.model.PictureState
import com.apiguave.onboarding_ui.extensions.filterIndex
import com.apiguave.onboarding_ui.extensions.toGender
import com.apiguave.onboarding_ui.extensions.toOrientation
import com.apiguave.onboarding_domain.CreateProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class CreateProfileViewModel(
    private val createProfileUseCase: CreateProfileUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateProfileViewState())
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
        _uiState.update { it.copy(dialogState = CreateProfileDialogState.NoDialog) }
    }

    fun showConfirmDeletionDialog(index: Int) {
        _uiState.update { it.copy(dialogState = CreateProfileDialogState.DeleteConfirmationDialog(index)) }
    }

    fun showSelectPictureDialog() {
        _uiState.update { it.copy(dialogState = CreateProfileDialogState.SelectPictureDialog) }
    }

    fun removePictureAt(index: Int) {
        _uiState.update { it.copy(pictures = it.pictures.filterIndex(index)) }
    }

    fun addPicture(picture: Uri) {
        _uiState.update { it.copy(pictures = it.pictures + PictureState.Local(picture)) }
    }

    fun createProfile() {
        _uiState.update { it.copy(dialogState = CreateProfileDialogState.Loading) }
        viewModelScope.launch {
            val name = _uiState.value.name.text
            val birthdate = _uiState.value.birthDate
            val bio = _uiState.value.bio.text
            val gender = _uiState.value.genderIndex.toGender()
            val orientation = _uiState.value.orientationIndex.toOrientation()
            val pictures = _uiState.value.pictures

            val result = createProfileUseCase(name, birthdate, bio, gender, orientation, pictures.map { it.uri.toString() })

            result.fold({
                _uiState.update { it.copy(isUserSignedIn = true) }
            }, { e ->
                _uiState.update { it.copy(dialogState = CreateProfileDialogState.ErrorDialog) }
            })

        }
    }
}

@Immutable
sealed class CreateProfileDialogState {
    data object NoDialog: CreateProfileDialogState()
    data class DeleteConfirmationDialog(val index: Int): CreateProfileDialogState()
    data object ErrorDialog: CreateProfileDialogState()
    data object SelectPictureDialog: CreateProfileDialogState()
    data object Loading: CreateProfileDialogState()
}

@Immutable
data class CreateProfileViewState(
    val name: TextFieldValue = TextFieldValue(),
    val maxBirthDate: LocalDate = UserAgePolicy.getMaxBirthdate(),
    val birthDate: LocalDate = UserAgePolicy.getMaxBirthdate(),
    val bio: TextFieldValue = TextFieldValue(),
    val genderIndex: Int = -1,
    val orientationIndex: Int = -1,
    val pictures: List<PictureState.Local> = emptyList(),
    val isUserSignedIn: Boolean = false,
    val dialogState: CreateProfileDialogState = CreateProfileDialogState.NoDialog
)