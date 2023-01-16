package com.apiguave.tinderclonecompose.ui.shared

import android.R
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.apiguave.tinderclonecompose.ui.ColumnCount
import com.apiguave.tinderclonecompose.ui.theme.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

//Picture Grid components

@Composable
fun PictureGridRow(rowIndex: Int, imageUris: SnapshotStateList<Uri>, onAddPicture: () -> Unit, onAddedPictureClicked: (Int) -> Unit){
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
                    onClick = { onAddedPictureClicked(cellIndex) }
                )
            } else {
                EmptyPictureItem(modifier = Modifier
                    .weight(1f)
                    .aspectRatio(.6f), onClick = onAddPicture )
            }
        }
    }
}
@Composable
fun EmptyPictureItem(modifier: Modifier = Modifier, onClick: () -> Unit){
    Box(modifier = modifier.clickable(onClick = onClick)) {
        Card(
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.6f)
                .padding(all = 8.dp),
            backgroundColor = if(isSystemInDarkTheme()) NightRider else LightLightGray, content = {
                val borderColor = if(isSystemInDarkTheme()) Color.DarkGray else SystemGray4
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRoundRect(color = borderColor, style = Stroke(width = 4.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(5.dp.toPx(), 5.dp.toPx()), 0f))
                    )
                }
            }
        )

        Icon(
            Icons.Filled.Add,
            tint = Color.White,
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Pink,
                            Orange,
                        )
                    ), CircleShape
                )
                .padding(2.dp)
                .align(Alignment.BottomEnd),
            contentDescription = null
        )
    }

}

@Composable
fun SelectedPictureItem(imageUri: Uri,
                        modifier: Modifier = Modifier,
                        onClick: () -> Unit){
    Box(modifier = modifier.clickable(onClick = onClick)) {
        Card(
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.6f)
                .padding(all = 8.dp),
            content = {
                AsyncImage(
                    model = imageUri,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null
                )
            }
        )

        Icon(
            Icons.Filled.Close,
            tint = Orange,
            modifier = Modifier
                .background(color = Color.White, shape = CircleShape)
                .padding(2.dp)
                .align(Alignment.BottomEnd),
            contentDescription = null
        )
    }

}

@Composable
fun OptionButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit, isSelected: Boolean){
    TextButton(
        modifier = modifier.border(BorderStroke(1.dp, if(isSystemInDarkTheme()) Color.DarkGray else SystemGray4)),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = if(isSystemInDarkTheme()){
                if(isSelected) Color.DarkGray else Nero
            } else {
                if(isSelected) Color.White else BasicWhite
            }),
        onClick = onClick,
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        )
    ) {
        Text(text, color = MaterialTheme.colors.onSurface)
    }
}

@Composable
fun SectionTitle(title: String){
    Text(
        title.uppercase(),
        modifier = Modifier.padding(all = 8.dp),
        color = if(isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun FormDivider(){
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(if (isSystemInDarkTheme()) Color.DarkGray else SystemGray4))
}

@Composable
fun TextRow(title: String, text: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isSystemInDarkTheme()) Nero else Color.White)
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontWeight = FontWeight.Bold, color = MaterialTheme.colors.onSurface)
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = text,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun FormTextField(value: TextFieldValue, placeholder: String, onValueChange: (TextFieldValue) -> Unit){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp),
        value = value,
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.onSurface,
            backgroundColor = if(isSystemInDarkTheme()) Nero else Color.White,
            unfocusedBorderColor = if(isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,
        ),
        onValueChange = onValueChange
    )
}

@Composable
fun GenderOptions(selectedIndex: Int, onOptionClick: (Int) -> Unit){
    val genderOptions = stringArrayResource(id = com.apiguave.tinderclonecompose.R.array.genders)
    Row(Modifier.fillMaxWidth()){
        genderOptions.forEachIndexed {index, s ->
            OptionButton(
                modifier = Modifier.weight(1.0f),
                text = s,
                onClick = { onOptionClick(index)},
                isSelected = selectedIndex == index)
        }
    }
}

@Composable
fun OrientationOptions(selectedIndex: Int, onOptionClick: (Int) -> Unit){
    val orientationOptions = stringArrayResource(id = com.apiguave.tinderclonecompose.R.array.interests)
    Row(Modifier.fillMaxWidth()){
        orientationOptions.forEachIndexed {index, s ->
            OptionButton(
                modifier = Modifier.weight(1.0f),
                text = s,
                onClick = { onOptionClick(index) },
                isSelected = selectedIndex == index)
        }
    }
}

//Dialogs
@Composable
fun DeleteConfirmationDialog(onDismissRequest: () -> Unit,
                             onConfirm: () -> Unit,
                             onDismiss: () -> Unit ){
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = "Delete confirmation")
        },
        text = {
            Text("Are you sure you want to delete this picture?")
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun FormDatePickerDialog(state: MaterialDialogState, onDateChange: (LocalDate) -> Unit){
    MaterialDialog(
        dialogState = state,
        buttons = {
            positiveButton(text = stringResource(id = R.string.ok))
            negativeButton(text = stringResource(id = R.string.cancel))
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = stringResource(id = com.apiguave.tinderclonecompose.R.string.pick_a_date),
            allowedDateValidator = {
                it.dayOfMonth % 2 == 1
            },
            onDateChange = onDateChange
        )
    }
}