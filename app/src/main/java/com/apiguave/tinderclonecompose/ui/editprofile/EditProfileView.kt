package com.apiguave.tinderclonecompose.ui.editprofile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.domain.profilecard.entity.CurrentProfile
import com.apiguave.tinderclonecompose.domain.profile.entity.UserPicture
import com.apiguave.tinderclonecompose.ui.components.*
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun EditProfileView(
    uiState: EditProfileUiState,
    signOut: () -> Unit,
    addPicture: () -> Unit,
    onSignedOut: () -> Unit,
    onProfileEdited: () -> Unit,
    removePictureAt: (Int) -> Unit,
    updateProfile: (currentProfile: CurrentProfile, bio: String, genderIndex: Int, orientationIndex: Int, pictures: List<UserPicture>) -> Unit,
    action: SharedFlow<EditProfileAction>,
) {

    var showErrorDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var deleteConfirmationPictureIndex by remember { mutableStateOf(-1) }

    var bioText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue(uiState.currentProfile.bio)) }
    var selectedGenderIndex by rememberSaveable { mutableStateOf(uiState.currentProfile.genderIndex) }
    var selectedOrientationIndex by rememberSaveable { mutableStateOf(uiState.currentProfile.orientationIndex) }

    LaunchedEffect(key1 = Unit, block = {
        action.collect {
            when(it){
                EditProfileAction.ON_SIGNED_OUT -> onSignedOut()
                EditProfileAction.ON_PROFILE_EDITED -> onProfileEdited()
            }
        }
    })

    LaunchedEffect(key1 = uiState.errorMessage, block = {
        if(uiState.errorMessage != null){
            showErrorDialog = true
        }
    })

    if (showDeleteConfirmationDialog) {
        DeleteConfirmationDialog(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            onConfirm = {
                showDeleteConfirmationDialog = false
                removePictureAt(deleteConfirmationPictureIndex)
            },
            onDismiss = { showDeleteConfirmationDialog = false })
    }

    if(showErrorDialog){
        ErrorDialog(
            errorDescription = uiState.errorMessage,
            onDismissRequest = { showErrorDialog = false },
            onConfirm = { showErrorDialog = false}
        )
    }

    Scaffold(
        topBar = {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = stringResource(id = R.string.edit_profile),
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                TextButton(onClick = {
                    updateProfile(uiState.currentProfile, bioText.text, selectedGenderIndex, selectedOrientationIndex, uiState.pictures)
                }) {
                    Text(text = stringResource(id = R.string.done))
                }
            }
        }){ padding ->
        Column(modifier = Modifier
            .padding(padding)
            .verticalScroll(rememberScrollState())) {
            repeat(RowCount){rowIndex ->
                PictureGridRow(
                    rowIndex = rowIndex,
                    pictures = uiState.pictures,
                    onAddPicture = addPicture,
                    onAddedPictureClicked = {
                        showDeleteConfirmationDialog = true
                        deleteConfirmationPictureIndex = it
                    }
                )
            }

            Spacer(Modifier.height(32.dp))
            Column(Modifier.fillMaxWidth()) {
                SectionTitle(title = stringResource(id = R.string.about_me))
                FormTextField(
                    value = bioText,
                    placeholder = stringResource(id = R.string.write_something_interesting),
                    onValueChange = {
                        bioText = it
                    })

                SectionTitle(title = stringResource(id = R.string.gender))
                HorizontalPicker(
                    id = R.array.genders,
                    selectedIndex = selectedGenderIndex,
                    onOptionClick = {
                        selectedGenderIndex = it

                    })

                SectionTitle(title = stringResource(id = R.string.i_am_interested_in))

                HorizontalPicker(
                    id = R.array.interests,
                    selectedIndex = selectedOrientationIndex,
                    onOptionClick = {
                        selectedOrientationIndex = it
                    })


                SectionTitle(title = stringResource(id = R.string.personal_information))
                FormDivider()
                TextRow(title = stringResource(id = R.string.name), text = uiState.currentProfile.name)
                FormDivider()
                TextRow(title = stringResource(id = R.string.birth_date), text = uiState.currentProfile.birthDate)
                FormDivider()

                Spacer(modifier = Modifier.height(32.dp))
                OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = signOut) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(id = R.string.sign_out), fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }


    if(uiState.isLoading){
        LoadingView()
    }
}

