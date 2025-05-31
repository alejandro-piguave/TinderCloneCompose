package com.apiguave.editprofile_ui

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.core_ui.model.PictureState
import com.apiguave.auth_domain.usecases.SignOutUseCase
import com.apiguave.picture_domain.model.LocalPicture
import com.apiguave.picture_domain.model.RemotePicture
import com.apiguave.picture_domain.usecases.GetPictureUseCase
import com.apiguave.profile_domain.model.UserProfile
import com.apiguave.editprofile_ui.extensions.filterIndex
import com.apiguave.editprofile_ui.extensions.getTaskResult
import com.apiguave.editprofile_ui.extensions.toGender
import com.apiguave.editprofile_ui.extensions.toLongString
import com.apiguave.editprofile_ui.extensions.toOrientation
import com.apiguave.editprofile_ui.usecases.UpdatePicturesUseCase
import com.apiguave.profile_domain.usecases.GetProfileUseCase
import com.apiguave.profile_domain.usecases.UpdateProfileUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val getPictureUseCase: GetPictureUseCase,
    private val updatePicturesUseCase: UpdatePicturesUseCase
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

    fun loadUserProfile() = viewModelScope.launch {
        getProfileUseCase().onSuccess { success ->
            success?.let { currentProfile ->
                _uiState.update {
                    it.copy(
                        currentProfile = currentProfile,
                        name = currentProfile.name,
                        bio = TextFieldValue(currentProfile.bio),
                        birthDate = currentProfile.birthDate.toLongString(),
                        genderIndex = currentProfile.gender.ordinal,
                        orientationIndex = currentProfile.orientation.ordinal,
                        pictures = currentProfile.pictureNames.map { pictureName -> PictureState.Loading(pictureName) }
                    )
                }
                loadProfilePictures(currentProfile.id, currentProfile.pictureNames)
            }
        }
    }

    private suspend fun loadProfilePictures(userId: String, pictureNames: List<String>) {
        pictureNames.forEach { pictureName ->
            Log.i("EditProfileViewModel", "loadProfilePictures($userId, $pictureName)")
            viewModelScope.launch {
                getPictureUseCase(userId, pictureName).onSuccess { pictureUrl ->
                    updatePicturesState(pictureName, Uri.parse(pictureUrl))
                }
            }
        }
    }

    private fun updatePicturesState(pictureName: String, pictureUrl: Uri) {
        _uiState.update {
            it.copy(
                pictures = it.pictures.map { pictureState ->
                    if(pictureState is PictureState.Loading && pictureState.name == pictureName)
                        PictureState.Remote(pictureName, pictureUrl)
                    else pictureState
                }
            )
        }
    }

    fun updateProfile() {
        _uiState.update { it.copy(dialogState = EditProfileDialogState.Loading) }

        viewModelScope.launch {
            val currentBio = _uiState.value.bio.text
            val currentGender = _uiState.value.genderIndex.toGender()
            val currentOrientation = _uiState.value.orientationIndex.toOrientation()

            updateProfileUseCase(currentBio, currentGender, currentOrientation).onFailure { e ->
                _uiState.update { it.copy(dialogState = EditProfileDialogState.ErrorDialog(e.message ?: "")) }
            }
        }

        viewModelScope.launch {
            val currentPictures = _uiState.value.pictures
            if(currentPictures.any { it is PictureState.Loading }) return@launch

            updatePicturesUseCase(currentPictures.mapNotNull { when(it) {
                is PictureState.Loading -> null
                is PictureState.Local -> LocalPicture(it.uri.toString())
                is PictureState.Remote -> RemotePicture(it.uri.toString(), it.name)
            } }).onSuccess {
                _action.emit(EditProfileAction.ON_PROFILE_EDITED)
            }
        }
    }

    fun addPicture(picture: Uri){
        _uiState.update { it.copy(pictures = it.pictures + PictureState.Local(picture)) }
    }

    fun removePictureAt(index: Int){
        _uiState.update { it.copy(pictures = it.pictures.filterIndex(index)) }
    }

    fun signOut(signInClient: GoogleSignInClient) = viewModelScope.launch {
        signOutUseCase().onSuccess {
            signInClient.signOut().getTaskResult()
            _action.emit(EditProfileAction.ON_SIGNED_OUT)
        }
    }

}

@Immutable
sealed class EditProfileDialogState {
    object NoDialog: EditProfileDialogState()
    data class DeleteConfirmationDialog(val index: Int): EditProfileDialogState()
    data class ErrorDialog(val message: String): EditProfileDialogState()
    object SelectPictureDialog: EditProfileDialogState()
    object Loading: EditProfileDialogState()
}

@Immutable
data class EditProfileViewState(
    val currentProfile: UserProfile? = null,
    val name: String = "",
    val birthDate: String = "",
    val bio: TextFieldValue = TextFieldValue(),
    val genderIndex: Int = -1,
    val orientationIndex: Int = -1,
    val pictures: List<PictureState> = emptyList(),
    val dialogState: EditProfileDialogState = EditProfileDialogState.NoDialog
)

enum class EditProfileAction{ON_SIGNED_OUT, ON_PROFILE_EDITED}