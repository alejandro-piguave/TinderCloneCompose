package com.apiguave.tinderclonecompose.ui

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apiguave.tinderclonecompose.ui.shared.*
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

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colors.surface),
    ) {

        val rows = 1 + (GridItemCount -1) / ColumnCount
        item {
            Text(
                text = "Edit Profile",
                color = MaterialTheme.colors.onSurface,
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

        SectionTitle(title = "Personal Information")
        FormDivider()
        TextRow(title = "Name", text = "Alejandro")
        FormDivider()
        TextRow(title = "Birthdate", text = "Ago 10 2001")
        FormDivider()

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

