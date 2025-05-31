package com.apiguave.editprofile_ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditProfileScreen(
    onSignedOut: () -> Unit,
    onProfileEdited: () -> Unit) {
    val editProfileViewModel: EditProfileViewModel = koinViewModel()
    val uiState by editProfileViewModel.uiState.collectAsState()
    LaunchedEffect(key1 = Unit){
        editProfileViewModel.loadUserProfile()
    }

    LaunchedEffect(key1 = Unit, block = {
        editProfileViewModel.action.collect {
            when(it){
                EditProfileAction.ON_SIGNED_OUT -> onSignedOut()
                EditProfileAction.ON_PROFILE_EDITED -> onProfileEdited()
            }
        }
    })

    val signInClient: GoogleSignInClient = get()
    EditProfileView(
        uiState = uiState,
        onPictureSelected = editProfileViewModel::addPicture,
        removePictureAt = editProfileViewModel::removePictureAt,
        onSignOutClicked = { editProfileViewModel.signOut(signInClient) },
        updateProfile = editProfileViewModel::updateProfile,
        onBioChanged = { editProfileViewModel.setBio(it) },
        onGenderIndexChanged = { editProfileViewModel.setGenderIndex(it) },
        onOrientationIndexChanged = { editProfileViewModel.setOrientationIndex(it) },
        onDialogClosed = { editProfileViewModel.closeDialog() },
        onDeletePictureClicked = { editProfileViewModel.showConfirmDeletionDialog(it) },
        onSelectPictureClicked = { editProfileViewModel.showSelectPictureDialog() }
    )
}