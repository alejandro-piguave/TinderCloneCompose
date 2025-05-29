package com.apiguave.tinderclonecompose.signup

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.extension.isValidUsername
import com.apiguave.tinderclonecompose.components.*
import com.apiguave.tinderclonecompose.components.dialogs.DeleteConfirmationDialog
import com.apiguave.tinderclonecompose.components.dialogs.ErrorDialog
import com.apiguave.tinderclonecompose.components.dialogs.FormDatePickerDialog
import com.apiguave.tinderclonecompose.components.dialogs.SelectPictureDialog
import com.apiguave.core_ui.theme.TinderCloneComposeTheme
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun SignUpView(
    uiState: SignUpViewState,
    onPictureSelected: (Uri) -> Unit,
    removePictureAt: (Int) -> Unit,
    onSignUpClicked: () -> Unit,
    onCloseDialogClicked: () -> Unit,
    onSelectPictureClicked: () -> Unit,
    onDeletePictureClicked: (Int) -> Unit,
    onBirthDateChanged: (LocalDate) -> Unit,
    onNameChanged: (TextFieldValue) -> Unit,
    onBioChanged: (TextFieldValue) -> Unit,
    onGenderIndexChanged: (Int) -> Unit,
    onOrientationIndexChanged: (Int) -> Unit,
) {
    val dateDialogState = rememberMaterialDialogState()
    val isSignUpEnabled = remember(uiState) { derivedStateOf { uiState.name.text.isValidUsername() && uiState.pictures.size > 1 && uiState.genderIndex >= 0 && uiState.orientationIndex >= 0 } }

    FormDatePickerDialog(dateDialogState, date = uiState.birthDate, maxDate = uiState.maxBirthDate, onDateChange = onBirthDateChanged)
    
    Surface {
        LazyColumn( modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        ) {

            item {
                Text(
                    text = stringResource(id = R.string.create_profile),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 30.sp,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }

            items(RowCount){ rowIndex ->
                PictureGridRow(
                    rowIndex = rowIndex,
                    pictures = uiState.pictures,
                    onAddPicture = onSelectPictureClicked,
                    onAddedPictureClicked = onDeletePictureClicked
                )
            }

            item {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(32.dp))
                Column(Modifier.fillMaxWidth()) {
                    SectionTitle(title = stringResource(id = R.string.personal_information))
                    FormTextField(
                        value = uiState.name,
                        placeholder = stringResource(id = R.string.enter_your_name) ,
                        onValueChange = onNameChanged
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    1.dp,
                                    if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
                                )
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(id = R.string.birth_date), modifier = Modifier.padding(start = 8.dp), color = MaterialTheme.colors.onSurface)
                        Spacer(modifier = Modifier.weight(1.0f))
                        TextButton(
                            onClick = { dateDialogState.show() },
                            contentPadding = PaddingValues(
                                start = 20.dp,
                                top = 20.dp,
                                end = 20.dp,
                                bottom = 20.dp
                            )
                        ) {
                            Text(
                                uiState.birthDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)),
                                color = MaterialTheme.colors.onSurface
                            )
                        }
                    }

                    SectionTitle(title = stringResource(id = R.string.about_me) )
                    FormTextField(
                        modifier = Modifier.height(128.dp),
                        value = uiState.bio,
                        placeholder = stringResource(id = R.string.write_something_interesting),
                        onValueChange = onBioChanged
                    )

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

                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .height(32.dp))

                    GradientGoogleButton(enabled = isSignUpEnabled.value, onClick = onSignUpClicked)
                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .height(32.dp))
                }
            }
        }
    }

    //Dialogs
    when(uiState.dialogState) {
        is SignUpDialogState.DeleteConfirmationDialog -> {
            DeleteConfirmationDialog(
                onDismissRequest = onCloseDialogClicked,
                onConfirm = {
                    onCloseDialogClicked()
                    removePictureAt(uiState.dialogState.index)},
                onDismiss = onCloseDialogClicked)
        }
        is SignUpDialogState.ErrorDialog -> {
            ErrorDialog(
                errorDescription = stringResource(id = R.string.sign_up_error),
                onDismissRequest = onCloseDialogClicked,
                onConfirm = onCloseDialogClicked
            )
        }
        SignUpDialogState.Loading -> {
            LoadingView()
        }
        SignUpDialogState.SelectPictureDialog -> {
            SelectPictureDialog(onCloseClick = onCloseDialogClicked, onReceiveUri = {
                onCloseDialogClicked()
                onPictureSelected(it)
            })
        }
        else -> {}
    }

}

@Preview
@Composable
fun SignUpViewPreview() {
    TinderCloneComposeTheme {
        SignUpView(
            uiState = SignUpViewState(),
            onPictureSelected = {},
            removePictureAt = {},
            onSignUpClicked = {},
            onCloseDialogClicked = {},
            onSelectPictureClicked = {},
            onDeletePictureClicked = {},
            onBirthDateChanged = {},
            onNameChanged = {},
            onBioChanged = {},
            onGenderIndexChanged = {},
            onOrientationIndexChanged = {}
        )
    }
}
