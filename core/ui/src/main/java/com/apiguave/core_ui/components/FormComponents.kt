package com.apiguave.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.apiguave.core_ui.theme.Nero
import com.apiguave.core_ui.theme.SystemGray4

@Composable
fun SectionTitle(title: String) {
    Text(
        title.uppercase(),
        modifier = Modifier.padding(all = 8.dp),
        color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun FormDivider() {
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(if (isSystemInDarkTheme()) Color.DarkGray else SystemGray4)
    )
}

@Composable
fun TextRow(title: String, text: String) {
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
fun FormTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    placeholder: String,
    onValueChange: (TextFieldValue) -> Unit
) {
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
fun OptionButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
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


