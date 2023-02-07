package com.apiguave.tinderclonecompose.ui.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.CurrentProfile
import com.apiguave.tinderclonecompose.data.DevicePicture
import com.apiguave.tinderclonecompose.data.UserPicture
import com.apiguave.tinderclonecompose.data.repository.AuthRepository
import com.apiguave.tinderclonecompose.data.repository.FirebaseRepository
import com.apiguave.tinderclonecompose.extensions.filterIndex
import com.apiguave.tinderclonecompose.extensions.getTaskResult
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EditProfileViewModel: ViewModel() {
    private val _userPictures = MutableStateFlow(EditProfileUiState(false, emptyList(), null))
    val userPictures = _userPictures.asStateFlow()

    private val _action = MutableSharedFlow<EditProfileAction>()
    val action = _action.asSharedFlow()

    var currentProfile = CurrentProfile()
        set(value) {
            _userPictures.update { it.copy(pictures = value.pictures) }
            field = value
        }

    fun updateProfile(uiBio: String, uiGenderIndex: Int, uiOrientationIndex: Int, uiPictures: List<UserPicture>){
        val pictures = if(uiPictures == currentProfile.pictures) emptyList() else uiPictures
        val data = currentProfile.toModifiedData(uiBio, uiGenderIndex, uiOrientationIndex)

        viewModelScope.launch {
            //If no information has been changed, simply leave the screen
            if(pictures.isEmpty() && data.isEmpty()){
                _action.emit(EditProfileAction.ON_PROFILE_EDITED)
                return@launch
            }

            //Otherwise show loading and perform update operations
            _userPictures.update { it.copy(isLoading = true, errorMessage = null) }
            try{
                if(pictures.isNotEmpty() && data.isNotEmpty()){
                    FirebaseRepository.updateProfileDataAndPictures(data, currentProfile.pictures, pictures)
                } else if(pictures.isEmpty() && data.isNotEmpty()){
                    FirebaseRepository.updateProfileData(data)
                } else if(pictures.isNotEmpty()){
                    FirebaseRepository.updateProfilePictures(currentProfile.pictures, pictures)
                }

                //TODO: update field 'CurrentProfile' to display the information correctly once it's uploaded

                _userPictures.update { it.copy(isLoading = false) }
                _action.emit(EditProfileAction.ON_PROFILE_EDITED)
            }catch (e: Exception){
                _userPictures.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun addPicture(picture: DevicePicture){
        _userPictures.update { it.copy(pictures = it.pictures + picture) }
    }

    fun removePictureAt(index: Int){
        _userPictures.update { it.copy(pictures = it.pictures.filterIndex(index)) }
    }

    fun signOut(signInClient: GoogleSignInClient){
        viewModelScope.launch {
            AuthRepository.signOut()
            signInClient.signOut().getTaskResult()
            _action.emit(EditProfileAction.ON_SIGNED_OUT)
        }
    }

}

data class EditProfileUiState(val isLoading: Boolean, val pictures: List<UserPicture>, val errorMessage: String? = null)

enum class EditProfileAction{ON_SIGNED_OUT, ON_PROFILE_EDITED}