package com.apiguave.tinderclonecompose.ui

import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.ui.shared.*
import com.apiguave.tinderclonecompose.ui.theme.Nero
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun SignUpView(imageUris: SnapshotStateList<Uri>, onAddPicture: () -> Unit) {
    var deleteConfirmationDialog by remember { mutableStateOf(false) }
    var deleteConfirmationPictureIndex by remember { mutableStateOf(-1) }
    var birthdate by remember { mutableStateOf(LocalDate.now()) }
    val dateDialogState = rememberMaterialDialogState()
    var nameText by remember { mutableStateOf(TextFieldValue("")) }
    var bioText by remember { mutableStateOf(TextFieldValue("")) }

    var selectedGenderIndex by remember { mutableStateOf(0) }
    var selectedOrientationIndex by remember { mutableStateOf(0) }

    if (deleteConfirmationDialog) {
        DeleteConfirmationDialog(
            onDismissRequest = { deleteConfirmationDialog = false },
            onConfirm = {
                deleteConfirmationDialog = false
                imageUris.removeAt(deleteConfirmationPictureIndex) },
            onDismiss = { deleteConfirmationDialog = false})
    }
    FormDatePickerDialog(dateDialogState,onDateChange = { birthdate = it })

    LazyColumn( modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(MaterialTheme.colors.surface),
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
            Spacer(Modifier.fillMaxWidth().height(32.dp))
            Column(Modifier.fillMaxWidth()) {
                SectionTitle(title = stringResource(id = R.string.personal_information))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = nameText,
                    placeholder = { Text(stringResource(id = R.string.enter_your_name)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = if (isSystemInDarkTheme()) Nero else Color.White,
                        unfocusedBorderColor =  if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,
                    ),
                    onValueChange = { newText ->
                        nameText = newText
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isSystemInDarkTheme()) Nero else Color.White)
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
                FormTextField(value = bioText, placeholder = stringResource(id = R.string.write_something_interesting), onValueChange = {
                    bioText = it
                })

                SectionTitle(title = stringResource(id = R.string.gender))
                GenderOptions(
                    selectedIndex = selectedGenderIndex,
                    onOptionClick = { selectedGenderIndex = it })

                SectionTitle(title = stringResource(id = R.string.i_am_interested_in))
                OrientationOptions(
                    selectedIndex = selectedOrientationIndex,
                    onOptionClick = { selectedOrientationIndex = it })

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp))
                OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {}){
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                        Image(
                            painter = painterResource(id = R.drawable.google_logo_48),
                            contentDescription = null
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(stringResource(id = R.string.sign_up_with_google), color = Color.Gray)
                    }
                }
                Spacer(Modifier.fillMaxWidth().height(32.dp))
            }
        }
    }
}
