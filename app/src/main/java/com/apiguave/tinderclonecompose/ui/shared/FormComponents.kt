package com.apiguave.tinderclonecompose.ui.shared

import android.R.string
import android.net.Uri
import androidx.annotation.ArrayRes
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.ui.theme.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import kotlinx.coroutines.launch
import java.time.LocalDate

//Picture Grid components

const val ColumnCount = 3
const val GridItemCount = 9
const val RowCount = 1 + (GridItemCount -1) / ColumnCount

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

//Form components

@Composable
fun GradientGoogleButton(enabled: Boolean, onClick: () -> Unit){
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        contentPadding = PaddingValues(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = onClick
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (enabled) 1f else .12f)
                .background(Brush.horizontalGradient(listOf(Pink, Orange)))
                .padding(vertical = 16.dp)
            ,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = painterResource(id = R.drawable.google_logo_48),
                contentDescription = null
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(id = R.string.sign_up_with_google),
                color = Color.White)
        }
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
fun FormTextField(modifier: Modifier = Modifier, value: TextFieldValue, placeholder: String, onValueChange: (TextFieldValue) -> Unit){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        value = value,
        placeholder = { Text(placeholder) },
        onValueChange = onValueChange
    )
}

@Composable
fun OptionButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit){
    TextButton(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        )
    ) {
        Text(text, color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
    }
}

@Composable
fun HorizontalPicker(@ArrayRes id: Int, selectedIndex: Int, onOptionClick: (Int) -> Unit){
    val options = stringArrayResource(id = id)
    val coroutineScope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }

    val density = LocalDensity.current
    var itemWidth by remember { mutableStateOf(0f) }
    var itemHeight by remember { mutableStateOf(0f) }

    Box{
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(
                        1.dp,
                        if (isSystemInDarkTheme()) Color.DarkGray else SystemGray4
                    )
                )
                .onGloballyPositioned {
                    itemWidth = density.run { (it.size.width / options.size).toDp().value }
                    itemHeight = density.run { it.size.height.toDp() }.value
                }
        ){
            options.forEachIndexed {index, s ->
                OptionButton(
                    modifier = Modifier.weight(1.0f),
                    text = s,
                    onClick = {
                        coroutineScope.launch {
                            offsetX.animateTo(targetValue = itemWidth*index )
                        }
                        onOptionClick(index)
                    })
            }
        }

        Surface(elevation = 4.dp, modifier = Modifier
            .offset(x = offsetX.value.dp)
            .size(width = itemWidth.dp, height = itemHeight.dp)
            .padding(4.dp)) {


                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Brush.horizontalGradient(listOf(Pink, Orange)))
                        .fillMaxSize()
                    ,
                    contentAlignment = Alignment.Center) {
                    Text(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        text = options[selectedIndex],
                    )
                }

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
            Text(stringResource(id = R.string.delete_confirmation_title))
        },
        text = {
            Text(stringResource(id = R.string.delete_confirmation_body))
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm) {
                Text(stringResource(id = R.string.delete))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )
}

val eighteenYearsAgo: LocalDate = LocalDate.now().minusYears(18L)

@Composable
fun FormDatePickerDialog(state: MaterialDialogState, onDateChange: (LocalDate) -> Unit){
    MaterialDialog(
        dialogState = state,
        buttons = {
            positiveButton(text = stringResource(id = string.ok))
            negativeButton(text = stringResource(id = string.cancel))
        }
    ) {
        datepicker(
            initialDate = eighteenYearsAgo,
            title = stringResource(id = com.apiguave.tinderclonecompose.R.string.pick_a_date),
            allowedDateValidator = {
                it.isBefore(eighteenYearsAgo)
            },
            onDateChange = onDateChange
        )
    }
}