package com.apiguave.tinderclonecompose.ui

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apiguave.tinderclonecompose.ui.theme.BasicWhite
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
fun EditProfileView(imageUris: SnapshotStateList<Uri>, onAddPicture: () -> Unit) {

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
        .background(BasicWhite),
    ) {

        val rows = 1 + (GridItemCount -1) / ColumnCount
        item {
            Text(
                text = "Edit Profile",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 30.sp,
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
            EditProfileFormView()
        }
    }
}

@Composable
fun EditProfileFormView(){
    var bioText by remember { mutableStateOf(TextFieldValue("")) }

    val genderOptions = listOf("Hombre", "Mujer")
    var selectedGenderIndex by remember { mutableStateOf(0) }

    val orientationOptions = listOf("Hombres", "Mujeres", "Ambos")
    var selectedOrientationIndex by remember { mutableStateOf(0) }


    Column(Modifier.fillMaxWidth()) {
        SectionTitle(title = "About me" )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp),
            value = bioText,
            placeholder = { Text("Pon algo interesante...") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
            ),
            onValueChange = { newText ->
                bioText = newText
            }
        )

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

        SectionTitle(title = "Personal Information")
        TextRow(title = "Name", text = "Alejandro")
        TextRow(title = "Birthdate", text = "Ago 10 2001")

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(32.dp))
        OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {}){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                Text("Sign Out", fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(32.dp))

    }
}


@Composable
fun TextRow(title: String, text: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(BorderStroke(1.dp, Color.LightGray))
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = text,
            color = Color.Black
        )
    }
}