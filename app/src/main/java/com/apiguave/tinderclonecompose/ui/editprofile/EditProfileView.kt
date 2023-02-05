package com.apiguave.tinderclonecompose.ui.editprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.ui.components.*
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@Composable
fun EditProfileView(
    signInClient: GoogleSignInClient,
    onAddPicture: () -> Unit,
    onSignedOut: () -> Unit,
    viewModel: EditProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var deleteConfirmationDialog by remember { mutableStateOf(false) }
    var deleteConfirmationPictureIndex by remember { mutableStateOf(-1) }

    var bioText by remember { mutableStateOf(TextFieldValue(uiState.currentProfile?.bio ?: "")) }

    var selectedGenderIndex by remember {
        mutableStateOf(uiState.currentProfile?.isMale?.let { if(it) 0 else 1 } ?: -1)
    }
    var selectedOrientationIndex by remember { mutableStateOf(uiState.currentProfile?.orientation?.ordinal ?: -1) }

    LaunchedEffect(key1 = uiState.isUserSignedOut, block = {
        if(uiState.isUserSignedOut){
            onSignedOut()
        }
    })

    if (deleteConfirmationDialog) {
        DeleteConfirmationDialog(
            onDismissRequest = { deleteConfirmationDialog = false },
            onConfirm = {
                deleteConfirmationDialog = false
                viewModel.removePictureAt(deleteConfirmationPictureIndex)
            },
            onDismiss = { deleteConfirmationDialog = false })
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.surface),
    ) {
        item {
            Text(
                text = stringResource(id = R.string.edit_profile),
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(RowCount) { rowIndex ->
            PictureGridRow(
                rowIndex = rowIndex,
                imageUris = uiState.currentProfile?.pictures ?: emptyList(),
                onAddPicture = onAddPicture,
                onAddedPictureClicked = {
                    deleteConfirmationDialog = true
                    deleteConfirmationPictureIndex = it
                }
            )
        }

        item {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            )
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
                    onOptionClick = { selectedGenderIndex = it })

                SectionTitle(title = stringResource(id = R.string.i_am_interested_in))

                HorizontalPicker(
                    id = R.array.interests,
                    selectedIndex = selectedOrientationIndex,
                    onOptionClick = { selectedOrientationIndex = it })


                SectionTitle(title = stringResource(id = R.string.personal_information))
                FormDivider()
                TextRow(title = stringResource(id = R.string.name), text = uiState.currentProfile?.name ?: "")
                FormDivider()
                TextRow(title = stringResource(id = R.string.birth_date), text = uiState.currentProfile?.birthDate ?: "")
                FormDivider()

                Spacer(modifier = Modifier.fillMaxWidth().height(32.dp))
                OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = { viewModel.signOut(signInClient) }) {
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
                Spacer(modifier = Modifier.fillMaxWidth().height(32.dp))
            }
        }
    }
}

