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
import com.apiguave.tinderclonecompose.ui.theme.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

const val ColumnCount = 3
const val GridItemCount = 9

@Composable
fun SignUpView(imageUris: SnapshotStateList<Uri>, onAddPicture: () -> Unit) {

    var deleteConfirmationDialog by remember { mutableStateOf(false) }
    var deleteConfirmationPictureIndex by remember { mutableStateOf(-1) }
    var birthdate by remember { mutableStateOf(LocalDate.now()) }
    val dateDialogState = rememberMaterialDialogState()

    if (deleteConfirmationDialog) {
        DeleteConfirmationDialog(
            onDismissRequest = { deleteConfirmationDialog = false },
            onConfirm = {
                deleteConfirmationDialog = false
                imageUris.removeAt(deleteConfirmationPictureIndex) },
            onDismiss = { deleteConfirmationDialog = false})
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date",
            allowedDateValidator = {
                it.dayOfMonth % 2 == 1
            }
        ) {
            birthdate = it
        }
    }

    LazyColumn( modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(MaterialTheme.colors.surface),
        ) {

        val rows = 1 + (GridItemCount -1) / ColumnCount
        item {
            Text(
                text = "Crear Perfil",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 30.sp,
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold)
        }

        items(rows){ rowIndex ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)){
                repeat(ColumnCount) { columnIndex ->
                    val cellIndex = rowIndex * ColumnCount + columnIndex

                    if(cellIndex < imageUris.size){
                        SelectedPictureItem(
                            imageUri = imageUris[cellIndex],
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(.6f),
                            onClick = {
                                deleteConfirmationDialog = true
                                deleteConfirmationPictureIndex = cellIndex
                            }
                        )
                    } else {
                        EmptyPictureItem(modifier = Modifier
                            .weight(1f)
                            .aspectRatio(.6f), onClick = onAddPicture )
                    }
                }
            }
        }
        item{
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(32.dp))
        }

        item {
            CreateProfileFormView(onSelectBirthdateClick = {
                dateDialogState.show()
            }, birthdate = birthdate)
        }
    }
}

@Composable
fun CreateProfileFormView(onSelectBirthdateClick: () -> Unit, birthdate: LocalDate){
    var nameText by remember { mutableStateOf(TextFieldValue("")) }
    var bioText by remember { mutableStateOf(TextFieldValue("")) }


    val genderOptions = listOf("Hombre", "Mujer")
    var selectedGenderIndex by remember { mutableStateOf(0) }

    val orientationOptions = listOf("Hombres", "Mujeres", "Ambos")
    var selectedOrientationIndex by remember { mutableStateOf(0) }


    Column(Modifier.fillMaxWidth()) {
        SectionTitle(title = "Personal Information")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = nameText,
            placeholder = { Text("Introduce tu nombre") },
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
                    BorderStroke(1.dp, if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Fecha de nacimiento", modifier = Modifier.padding(start = 8.dp), color = MaterialTheme.colors.onSurface)
                    Spacer(modifier = Modifier.weight(1.0f))
                    TextButton(
                        onClick = onSelectBirthdateClick,
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

        SectionTitle(title = "Sobre mí" )
        FormTextField(value = bioText, placeholder = "Pon algo interesante...", onValueChange = {
            bioText = it
        })

        SectionTitle(title = "Género")

        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
        ){
            genderOptions.forEachIndexed {index, s ->
                OptionButton(
                    modifier = Modifier.weight(1.0f),
                    text = s,
                    onClick = { selectedGenderIndex = index },
                    isSelected = selectedGenderIndex == index)
            }
        }

        SectionTitle(title = "Me interesan")

        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
        ){
            orientationOptions.forEachIndexed {index, s ->
                OptionButton(
                    modifier = Modifier.weight(1.0f),
                    text = s,
                    onClick = { selectedOrientationIndex = index },
                    isSelected = selectedOrientationIndex == index)
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(32.dp))
        OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {}){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = R.drawable.google_logo_48),
                    contentDescription = stringResource(id = R.string.app_name)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Sign Up with Google", color = Color.Gray)
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(32.dp))

    }
}
