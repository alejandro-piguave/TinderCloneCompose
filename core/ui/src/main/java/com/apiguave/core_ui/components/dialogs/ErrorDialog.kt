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