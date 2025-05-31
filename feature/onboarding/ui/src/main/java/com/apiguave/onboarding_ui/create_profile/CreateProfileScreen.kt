package com.apiguave.onboarding_ui.create_profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateProfileScreen(
    onNavigateToHome: () -> Unit) {
    val createProfileViewModel: CreateProfileViewModel = koinViewModel()
    val uiState by createProfileViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = uiState, block = {
        if(uiState.isUserSignedIn){
            onNavigateToHome()
        }
    })

    CreateProfileView(
        uiState = uiState,
        onPictureSelected = createProfileViewModel::addPicture,
        removePictureAt = createProfileViewModel::removePictureAt,
        onSignUpClicked = createProfileViewModel::createProfile,
        onCloseDialogClicked = createProfileViewModel::closeDialog,
        onDeletePictureClicked = createProfileViewModel::showConfirmDeletionDialog,
        onSelectPictureClicked = createProfileViewModel::showSelectPictureDialog,
        onBirthDateChanged = createProfileViewModel::setBirthDate,
        onNameChanged = createProfileViewModel::setName,
        onBioChanged = createProfileViewModel::setBio,
        onGenderIndexChanged = createProfileViewModel::setGenderIndex,
        onOrientationIndexChanged = createProfileViewModel::setOrientationIndex
    )
}