package com.apiguave.editprofile_ui

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apiguave.core_ui.components.FormDivider
import com.apiguave.core_ui.components.FormTextField
import com.apiguave.core_ui.components.HorizontalPicker
import com.apiguave.core_ui.components.LoadingView
import com.apiguave.core_ui.components.PictureGridRow
import com.apiguave.core_ui.components.RowCount
import com.apiguave.core_ui.components.SectionTitle
import com.apiguave.core_ui.components.TextRow
import com.apiguave.core_ui.components.dialogs.DeleteConfirmationDialog
import com.apiguave.core_ui.components.dialogs.ErrorDialog
import com.apiguave.core_ui.components.dialogs.SelectPictureDialog
import com.apiguave.core_ui.theme.TinderCloneComposeTheme

@Composable
fun EditProfileView(
    uiState: EditProfileViewState,
    onSignOutClicked: () -> Unit,
    onPictureSelected: (Uri) -> Unit,
    removePictureAt: (Int) -> Unit,
    updateProfile: () -> Unit,
    onBioChanged:(TextFieldValue) -> Unit,
    onGenderIndexChanged: (Int) -> Unit,
    onOrientationIndexChanged: (Int) -> Unit,
    onDialogClosed: () -> Unit,
    onSelectPictureClicked: () -> Unit,
    onDeletePictureClicked: (Int) -> Unit,
) {
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
                TextButton(onClick = updateProfile) {
                    Text(text = stringResource(id = R.string.done))
                }
            }
        }){ padding ->
        Column(modifier = Modifier
            .padding(padding)
            .verticalScroll(rememberScrollState())) {
            repeat(RowCount){ rowIndex ->
                PictureGridRow(
                    rowIndex = rowIndex,
                    pictures = uiState.pictures,
                    onAddPicture = onSelectPictureClicked,
                    onAddedPictureClicked = onDeletePictureClicked
                )
            }

            Spacer(Modifier.height(32.dp))
            Column(Modifier.fillMaxWidth()) {
                SectionTitle(title = stringResource(id = R.string.about_me))
                FormTextField(
                    value = uiState.bio,
                    placeholder = stringResource(id = R.string.write_something_interesting),
                    onValueChange = onBioChanged)

                SectionTitle(title = stringResource(id = R.string.gender))
                HorizontalPicker(
                    id = R.array.genders,
                    selectedIndex = uiState.genderIndex,
                    onOptionClick = onGenderIndexChanged)

                SectionTitle(title = stringResource(id = R.string.i_am_interested_in))

                HorizontalPicker(
                    id = R.array.interests,
                    selectedIndex = uiState.orientationIndex,
                    onOptionClick = onOrientationIndexChanged)


                SectionTitle(title = stringResource(id = R.string.personal_information))
                FormDivider()
                TextRow(title = stringResource(id = R.string.name), text = uiState.name)
                FormDivider()
                TextRow(title = stringResource(id = R.string.birth_date), text = uiState.birthDate)
                FormDivider()

                Spacer(modifier = Modifier.height(32.dp))
                OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = onSignOutClicked) {
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

    when(uiState.dialogState) {
        is EditProfileDialogState.DeleteConfirmationDialog -> {
            DeleteConfirmationDialog(
                onDismissRequest = onDialogClosed,
                onConfirm = {
                    onDialogClosed()
                    removePictureAt(uiState.dialogState.index)
                },
                onDismiss = onDialogClosed)
        }
        is EditProfileDialogState.ErrorDialog -> {
            ErrorDialog(
                errorDescription = uiState.dialogState.message,
                onDismissRequest = onDialogClosed,
                onConfirm = onDialogClosed
            )
        }
        EditProfileDialogState.Loading -> {
            LoadingView()
        }
        EditProfileDialogState.SelectPictureDialog -> {
            SelectPictureDialog(onCloseClick = onDialogClosed, onReceiveUri = {
                onDialogClosed()
                onPictureSelected(it)
            })
        }
        else -> {}
    }
}

@Preview
@Composable
fun EditProfileViewPreview() {
    TinderCloneComposeTheme {
        EditProfileView(
            uiState = EditProfileViewState(),
            onSignOutClicked = {},
            onPictureSelected = {},
            removePictureAt = {},
            updateProfile = {},
            onBioChanged = {},
            onGenderIndexChanged = {},
            onOrientationIndexChanged = {},
            onDialogClosed = {},
            onSelectPictureClicked = {},
            onDeletePictureClicked = {}
        )
    }
}