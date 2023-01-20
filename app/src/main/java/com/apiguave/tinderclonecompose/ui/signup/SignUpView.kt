package com.apiguave.tinderclonecompose.ui.signup

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.ui.shared.*
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun SignUpView(imageUris: SnapshotStateList<Uri>, onAddPicture: () -> Unit, signUpViewModel: SignUpViewModel = viewModel()) {
    var deleteConfirmationDialog by remember { mutableStateOf(false) }
    var deleteConfirmationPictureIndex by remember { mutableStateOf(0) }
    var birthdate by rememberSaveable { mutableStateOf(eighteenYearsAgo) }
    val dateDialogState = rememberMaterialDialogState()
    var nameText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var bioText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    var selectedGenderIndex by rememberSaveable { mutableStateOf(0) }
    var selectedOrientationIndex by rememberSaveable { mutableStateOf(0) }

    val isInformationValid = nameText.text.length in 3..30 && nameText.text.all { it.isLetter() } && imageUris.size > 1

    if (deleteConfirmationDialog) {
        DeleteConfirmationDialog(
            onDismissRequest = { deleteConfirmationDialog = false },
            onConfirm = {
                deleteConfirmationDialog = false
                imageUris.removeAt(deleteConfirmationPictureIndex) },
            onDismiss = { deleteConfirmationDialog = false})
    }
    FormDatePickerDialog(dateDialogState, onDateChange = { birthdate = it })

    Surface {
        LazyColumn( modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        ) {

            item {
                Text(
                    text = stringResource(id = R.string.create_profile),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 30.sp,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold)
            }

            items(RowCount){ rowIndex ->
                PictureGridRow(
                    rowIndex = rowIndex,
                    imageUris = imageUris,
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
                        .height(32.dp))
                Column(Modifier.fillMaxWidth()) {
                    SectionTitle(title = stringResource(id = R.string.personal_information))
                    FormTextField(
                        value = nameText,
                        placeholder = stringResource(id = R.string.enter_your_name) ,
                        onValueChange = { newText ->
                            nameText = newText
                        }
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
                                birthdate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)),
                                color = MaterialTheme.colors.onSurface
                            )
                        }
                    }

                    SectionTitle(title = stringResource(id = R.string.about_me) )
                    FormTextField(
                        modifier = Modifier.height(128.dp),
                        value = bioText,
                        placeholder = stringResource(id = R.string.write_something_interesting),
                        onValueChange = { bioText = it }
                    )

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

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp))

                    GradientGoogleButton(enabled = isInformationValid) {

                    }
                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .height(32.dp))
                }
            }
        }
    }
}
