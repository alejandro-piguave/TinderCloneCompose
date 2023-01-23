package com.apiguave.tinderclonecompose.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.apiguave.tinderclonecompose.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun DeleteConfirmationDialog(onDismissRequest: () -> Unit,
                             onConfirm: () -> Unit,
                             onDismiss: () -> Unit ){
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = stringResource(id = R.string.delete_confirmation_title), fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
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
            positiveButton(text = stringResource(id = android.R.string.ok))
            negativeButton(text = stringResource(id = android.R.string.cancel))
        }
    ) {
        datepicker(
            initialDate = eighteenYearsAgo,
            title = stringResource(id = R.string.pick_a_date),
            allowedDateValidator = {
                it.isBefore(eighteenYearsAgo)
            },
            onDateChange = onDateChange
        )
    }
}

@Composable
fun ErrorDialog(errorDescription: String?, onDismissRequest: () -> Unit,
                onConfirm: () -> Unit){
    AlertDialog(
        title = { Text(text = stringResource(id = R.string.error), fontWeight = FontWeight.SemiBold, fontSize = 16.sp) },
        text = {
            Text(text = errorDescription ?: "")
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm) {
                Text(stringResource(id = android.R.string.ok))
            }
        },
        onDismissRequest = onDismissRequest)
}