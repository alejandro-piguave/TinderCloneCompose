package com.apiguave.core_ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.apiguave.core_ui.theme.LightLightGray
import com.apiguave.core_ui.theme.UltramarineBlue
import com.apiguave.core_ui.R


@Composable
fun ChatFooter(modifier: Modifier = Modifier, onSendClicked: (String) -> Unit, shape: Shape = CircleShape) {
    var inputValue by remember { mutableStateOf("") } // 2

    Card(
        modifier = Modifier.padding(8.dp).then(modifier),
        border = BorderStroke(1.dp, LightLightGray),
        shape = shape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                // 4
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                value = inputValue,
                onValueChange = { inputValue = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions {
                    onSendClicked(inputValue)
                    inputValue = ""  },
            )
            TextButton(
                // 5
                onClick = {
                    onSendClicked(inputValue)
                    inputValue = ""
                },
                enabled = inputValue.isNotBlank(),
                colors = ButtonDefaults.textButtonColors(contentColor = UltramarineBlue)
            ) {
                Text(stringResource(id = R.string.send))
            }
        }

    }

}