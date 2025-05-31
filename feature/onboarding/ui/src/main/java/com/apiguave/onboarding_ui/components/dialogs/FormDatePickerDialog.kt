package com.apiguave.onboarding_ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.apiguave.onboarding_ui.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun FormDatePickerDialog(state: MaterialDialogState, date: LocalDate, maxDate: LocalDate, onDateChange: (LocalDate) -> Unit){
    MaterialDialog(
        dialogState = state,
        buttons = {
            positiveButton(text = stringResource(id = android.R.string.ok))
            negativeButton(text = stringResource(id = android.R.string.cancel))
        }
    ) {
        datepicker(
            initialDate = date,
            title = stringResource(id = R.string.pick_a_date),
            allowedDateValidator = {
                it.isBefore(maxDate) || it.isEqual(maxDate)
            },
            onDateChange = onDateChange
        )
    }
}

