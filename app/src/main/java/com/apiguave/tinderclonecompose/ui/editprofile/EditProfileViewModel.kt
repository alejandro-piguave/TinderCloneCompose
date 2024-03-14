package com.apiguave.tinderclonecompose.ui.editprofile

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.auth.AuthRepository
import com.apiguave.tinderclonecompose.data.profile.repository.ProfileRepository
import com.apiguave.tinderclonecompose.data.picture.repository.DevicePicture
import com.apiguave.tinderclonecompose.data.picture.repository.Picture
import com.apiguave.tinderclonecompose.ui.extension.filterIndex
import com.apiguave.tinderclonecompose.ui.extension.getTaskResult
import com.apiguave.tinderclonecompose.ui.extension.toGender
import com.apiguave.tinderclonecompose.ui.extension.toOrientation
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(
        EditProfileUiState()
    )
    val uiState = _uiState.asStateFlow()

    private val _action = MutableSharedFlow<EditProfileAction>()
    val action = _action.asSharedFlow()

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
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try{
                val currentBio = _uiState.value.bio.text
                val currentGender = _uiState.value.genderIndex.toGender()
                val currentOrientation = _uiState.value.orientationIndex.toOrientation()
                val currentPictures = _uiState.value.pictures
                val updatedProfile = profileRepository.updateProfile(currentBio, currentGender, currentOrientation, currentPictures)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        name = updatedProfile.name,
                        bio = TextFieldValue(updatedProfile.bio),
                        genderIndex = updatedProfile.gender.ordinal,
                        orientationIndex = updatedProfile.orientation.ordinal,
                        pictures = updatedProfile.pictures
                    )
                }
                _action.emit(EditProfileAction.ON_PROFILE_EDITED)
            }catch (e: Exception){
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun addPicture(picture: DevicePicture){
        _uiState.update { it.copy(pictures = it.pictures + picture) }
    }

    fun removePictureAt(index: Int){
        _uiState.update { it.copy(pictures = it.pictures.filterIndex(index)) }
    }

    fun signOut(signInClient: GoogleSignInClient){
        viewModelScope.launch {
            authRepository.signOut()
            signInClient.signOut().getTaskResult()
            _action.emit(EditProfileAction.ON_SIGNED_OUT)
        }
    }

}

data class EditProfileUiState(
    val name: String="",
    val birthDate: String = "",
    val bio: TextFieldValue = TextFieldValue(),
    val genderIndex: Int = -1,
    val orientationIndex: Int = -1,
    val pictures: List<Picture> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null)

enum class EditProfileAction{ON_SIGNED_OUT, ON_PROFILE_EDITED}