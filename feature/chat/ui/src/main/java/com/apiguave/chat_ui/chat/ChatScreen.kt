package com.apiguave.chat_ui.chat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.apiguave.chat_ui.R

@Composable
fun ChatScreen(
    onArrowBackPressed: () -> Unit,
    viewModel: ChatViewModel
) {
    val chatViewState by viewModel.viewState.collectAsState()
    chatViewState?.let {
        val messages by viewModel.getMessages(it.match.id).collectAsState(
            initial = listOf()
        )
        ChatView(
            state = it,
            messages = messages,
            onArrowBackPressed = onArrowBackPressed,
            sendMessage = viewModel::sendMessage,
        )
    }  ?: run{
        Text(modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center, text = stringResource(id = R.string.no_match_value_passed))
    }
}