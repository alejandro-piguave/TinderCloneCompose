package com.apiguave.auth_ui.register

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.apiguave.feature_auth.register.SignUpViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    onNavigateToHome: () -> Unit) {
    val signUpViewModel: SignUpViewModel = koinViewModel()
    val uiState by signUpViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = uiState, block = {
        if(uiState.isUserSignedIn){
            onNavigateToHome()
        }
    })

    val signInClient: GoogleSignInClient = get()
    val coroutineScope = rememberCoroutineScope()
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = signUpViewModel::signUp
    )
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