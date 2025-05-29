package com.apiguave.core_ui.components.dialogs

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.apiguave.core_ui.R

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