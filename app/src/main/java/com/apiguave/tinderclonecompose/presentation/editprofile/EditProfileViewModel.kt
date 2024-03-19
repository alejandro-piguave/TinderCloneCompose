package com.apiguave.tinderclonecompose.presentation.editprofile

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.profile.repository.ProfileRepository
import com.apiguave.tinderclonecompose.data.picture.LocalPicture
import com.apiguave.tinderclonecompose.data.picture.Picture
import com.apiguave.tinderclonecompose.presentation.extension.filterIndex
import com.apiguave.tinderclonecompose.presentation.extension.getTaskResult
import com.apiguave.tinderclonecompose.presentation.extension.toGender
import com.apiguave.tinderclonecompose.presentation.extension.toOrientation
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val profileRepository: ProfileRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(
        EditProfileViewState()
    )
    val uiState = _uiState.asStateFlow()

    private val _action = MutableSharedFlow<EditProfileAction>()
    val action = _action.asSharedFlow()

    fun setBio(bio: TextFieldValue) {
        _uiState.update { it.copy(bio = bio) }
    }

    fun setGenderIndex(genderIndex: Int) {
        _uiState.update { it.copy(genderIndex = genderIndex) }
    }

    fun setOrientationIndex(orientationIndex: Int) {
        _uiState.update { it.copy(orientationIndex = orientationIndex) }
    }

    fun closeDialog() {
        _uiState.update { it.copy(dialogState = EditProfileDialogState.NoDialog) }
    }

    fun showConfirmDeletionDialog(index: Int) {
        _uiState.update { it.copy(dialogState = EditProfileDialogState.DeleteConfirmationDialog(index)) }
    }

    fun showSelectPictureDialog() {
        _uiState.update { it.copy(dialogState = EditProfileDialogState.SelectPictureDialog) }
    }

    fun updateUserProfile(){
        viewModelScope.launch {
            val currentProfile = profileRepository.getProfile()
            _uiState.update {
                it.copy(
                    name = currentProfile.name,
                    bio = TextFieldValue(currentProfile.bio),
                    birthDate = currentProfile.birthDate,
                    genderIndex = currentProfile.gender.ordinal,
                    orientationIndex = currentProfile.orientation.ordinal,
                    pictures = currentProfile.pictures
                )
            }
        }
    }

    fun updateProfile(){
        viewModelScope.launch {
            //Otherwise show loading and perform update operations
            _uiState.update { it.copy(dialogState = EditProfileDialogState.Loading) }
            try{
                val currentBio = _uiState.value.bio.text
                val currentGender = _uiState.value.genderIndex.toGender()
                val currentOrientation = _uiState.value.orientationIndex.toOrientation()
                val currentPictures = _uiState.value.pictures
                val updatedProfile = profileRepository.updateProfile(currentBio, currentGender, currentOrientation, currentPictures)
                _uiState.update {
                    it.copy(
                        dialogState = EditProfileDialogState.Loading,
                        name = updatedProfile.name,
                        bio = TextFieldValue(updatedProfile.bio),
                        genderIndex = updatedProfile.gender.ordinal,
                        orientationIndex = updatedProfile.orientation.ordinal,
                        pictures = updatedProfile.pictures
                    )
                }
                _action.emit(EditProfileAction.ON_PROFILE_EDITED)
            }catch (e: Exception){
                _uiState.update { it.copy(dialogState = EditProfileDialogState.ErrorDialog(e.message ?: "")) }
            }
        }
    }

    fun addPicture(picture: LocalPicture){
        _uiState.update { it.copy(pictures = it.pictures + picture) }
    }

    fun removePictureAt(index: Int){
        _uiState.update { it.copy(pictures = it.pictures.filterIndex(index)) }
    }

    fun signOut(signInClient: GoogleSignInClient){
        viewModelScope.launch {
            profileRepository.signOut()
            signInClient.signOut().getTaskResult()
            _action.emit(EditProfileAction.ON_SIGNED_OUT)
        }
    }

}

sealed class EditProfileDialogState {
    object NoDialog: EditProfileDialogState()
    data class DeleteConfirmationDialog(val index: Int): EditProfileDialogState()
    data class ErrorDialog(val message: String): EditProfileDialogState()
    object SelectPictureDialog: EditProfileDialogState()
    object Loading: EditProfileDialogState()
}

data class EditProfileViewState(
    val name: String="",
    val birthDate: String = "",
    val bio: TextFieldValue = TextFieldValue(),
    val genderIndex: Int = -1,
    val orientationIndex: Int = -1,
    val pictures: List<Picture> = emptyList(),
    val dialogState: EditProfileDialogState = EditProfileDialogState.NoDialog
)

enum class EditProfileAction{ON_SIGNED_OUT, ON_PROFILE_EDITED}