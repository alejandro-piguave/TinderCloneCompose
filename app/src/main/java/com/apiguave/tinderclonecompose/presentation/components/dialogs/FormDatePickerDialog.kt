package com.apiguave.tinderclonecompose.presentation.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.apiguave.tinderclonecompose.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate


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

