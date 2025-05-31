package com.apiguave.home_ui

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.core_ui.model.ProfilePictureState
import com.apiguave.match_domain.model.Match
import com.apiguave.message_domain.usecases.SendMessageUseCase
import com.apiguave.picture_domain.usecases.GetPictureUseCase
import com.apiguave.profile_domain.model.Profile
import com.apiguave.profile_domain.usecases.GetProfilesUseCase
import com.apiguave.profile_domain.usecases.PassProfileUseCase
import com.apiguave.home_ui.usecases.LikeProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProfilesUseCase: GetProfilesUseCase,
    private val likeProfileUseCase: LikeProfileUseCase,
    private val passProfileUseCase: PassProfileUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getPictureUseCase: GetPictureUseCase
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(HomeViewState(HomeViewDialogState.NoDialog, HomeViewContentState.Loading))
    val uiState = _uiState.asStateFlow()

    init {
        fetchProfiles()
    }

    fun closeDialog() {
        _uiState.update {
            it.copy(dialogState = HomeViewDialogState.NoDialog)
        }
    }

    fun sendMessage(matchId: String, text: String) = viewModelScope.launch {
        sendMessageUseCase(matchId, text)
    }


    fun swipeUser(profileState: ProfileState, isLike: Boolean) = viewModelScope.launch {
        if (isLike) {
            likeProfileUseCase(profileState.profile).onSuccess { result ->
                result?.let { newMatch ->
                    _uiState.update {
                        it.copy(
                            dialogState = HomeViewDialogState.NewMatchDialog(newMatch, profileState.pictureStates)
                        )
                    }
                }
            }
        } else {
            passProfileUseCase(profileState.profile)
        }
    }

    fun removeLastProfile() {
        _uiState.update {
            if (it.contentState is HomeViewContentState.Success) {
                it.copy(
                    contentState = it.contentState.copy(
                        profileStates = it.contentState.profileStates.dropLast(
                            1
                        )
                    )
                )
            } else it
        }
    }

    fun fetchProfiles() = viewModelScope.launch {
        _uiState.update { it.copy(contentState = HomeViewContentState.Loading) }
        getProfilesUseCase().fold({ profiles ->
            _uiState.update { homeViewState ->
                homeViewState.copy(contentState = HomeViewContentState.Success(profileStates = profiles.map { profile ->
                    ProfileState(
                        profile,
                        profile.pictureNames.map { ProfilePictureState.Loading(it) })
                }))
            }

            profiles.forEach { profile ->
                loadProfilePictures(profile.id, profile.pictureNames)
            }

        }, { error ->
            _uiState.update {
                it.copy(
                    contentState = HomeViewContentState.Error(
                        error.message ?: ""
                    )
                )
            }
        })
    }

    private suspend fun loadProfilePictures(userId: String, pictureNames: List<String>) {
        pictureNames.forEach { pictureName ->
            viewModelScope.launch {
                getPictureUseCase(userId, pictureName).onSuccess { pictureUrl ->
                    updatePicturesState(userId, pictureName, Uri.parse(pictureUrl))
                }
            }
        }
    }

    private fun updatePicturesState(userId: String, pictureName: String, pictureUrl: Uri) {
        _uiState.update {
            if (it.contentState is HomeViewContentState.Success) {
                it.copy(contentState = it.contentState.copy(
                    profileStates = it.contentState.profileStates.map { profileState ->
                        if (profileState.profile.id == userId) {
                            profileState.copy(pictureStates = profileState.pictureStates.map { pictureState ->
                                if (pictureState is ProfilePictureState.Loading && pictureState.name == pictureName)
                                    ProfilePictureState.Remote(pictureUrl)
                                else pictureState
                            })
                        } else profileState
                    }
                )
                )
            } else it
        }
    }

}

@Immutable
data class HomeViewState(
    val dialogState: HomeViewDialogState,
    val contentState: HomeViewContentState
)

@Immutable
data class ProfileState(val profile: Profile, val pictureStates: List<ProfilePictureState>)

@Immutable
sealed class HomeViewDialogState {
    object NoDialog : HomeViewDialogState()
    data class NewMatchDialog(val match: Match, val pictureStates: List<ProfilePictureState>) : HomeViewDialogState()
}

@Immutable
sealed class HomeViewContentState {
    object Loading : HomeViewContentState()
    data class Success(val profileStates: List<ProfileState>) : HomeViewContentState()
    data class Error(val message: String) : HomeViewContentState()
}