package com.apiguave.tinderclonecompose.ui.signup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.apiguave.tinderclonecompose.data.profile.repository.CreateUserProfile
import com.apiguave.tinderclonecompose.data.profile.repository.Gender
import com.apiguave.tinderclonecompose.data.profile.repository.Orientation
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    signInClient: GoogleSignInClient,
    onNavigateToHome: () -> Unit) {
    val signUpViewModel: SignUpViewModel = koinViewModel()
    val uiState by signUpViewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { activityResult ->
            //Transforms the Uris to Bitmaps
            val gender = if(uiState.genderIndex == 0) Gender.MALE else Gender.FEMALE
            val orientation = Orientation.values()[uiState.genderIndex]
            val profile = CreateUserProfile(uiState.name.text, uiState.birthDate, uiState.bio.text, gender, orientation, uiState.pictures.map { it.bitmap })
            //Signs up with the information provided
            signUpViewModel.signUp(activityResult, profile)
        }
    )

    LaunchedEffect(key1 = uiState, block = {
        if(uiState.isUserSignedIn){
            onNavigateToHome()
        }
    })

    SignUpView(
        uiState = uiState,
        onPictureSelected = signUpViewModel::addPicture,
        removePictureAt = signUpViewModel::removePictureAt,
        onSignUpClicked = {
            coroutineScope.launch {
                startForResult.launch(signInClient.signInIntent)
            }
        },
        onCloseDialogClicked = signUpViewModel::closeDialog,
        onDeletePictureClicked = signUpViewModel::showConfirmDeletionDialog,
        onSelectPictureClicked = signUpViewModel::showSelectPictureDialog,
        onBirthDateChanged = signUpViewModel::setBirthDate,
        onNameChanged = signUpViewModel::setName,
        onBioChanged = signUpViewModel::setBio,
        onGenderIndexChanged = signUpViewModel::setGenderIndex,
        onOrientationIndexChanged = signUpViewModel::setOrientationIndex
    )
}