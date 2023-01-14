package com.apiguave.tinderclonecompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.domain.Message
import com.apiguave.tinderclonecompose.ui.shared.BlankAppBar
import com.apiguave.tinderclonecompose.ui.theme.AntiFlashWhite
import com.apiguave.tinderclonecompose.ui.theme.SystemGray4
import com.apiguave.tinderclonecompose.ui.theme.UltramarineBlue

@Composable
fun ChatView(onArrowBackPressed: () -> Unit) {
    val messages = listOf(
        Message("Text string 1", true),
        Message("Text string 2", false),
        Message("Text string 3", true),
        Message("Text string 4", false),
        Message("Text string 5", true),
        Message("Text string 6", false),

    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BlankAppBar(text = "Jane Doe", onArrowBackPressed = onArrowBackPressed)
        },
        bottomBar = { ChatFooter() }
    ) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding),
            reverseLayout = true
        ) {
            items(messages.size){ index ->
                MessageView(message = messages[index])
            }
        }
    }
}


@Composable
fun MessageView(message: Message) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if(message.isSender) Arrangement.End else Arrangement.Start) {
        Text(
            modifier = Modifier
                .background(if (message.isSender) UltramarineBlue else AntiFlashWhite, RoundedCornerShape(4.dp))
                .padding(6.dp),
            text = message.text,
            color = if(message.isSender) Color.White else Color.Black,
            )
    }
}

@Composable
fun ChatFooter() {
    var inputValue by remember { mutableStateOf("") } // 2
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            // 4
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            value = inputValue,
            onValueChange = { inputValue = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions { sendMessage() },
        )
        TextButton(
            // 5
            modifier = Modifier.height(56.dp),
            onClick = { sendMessage() },
            enabled = inputValue.isNotBlank(),
        ) {
            Text(stringResource(id = R.string.send))
        }
    }
}

fun sendMessage() {

}