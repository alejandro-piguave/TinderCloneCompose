package com.apiguave.tinderclonecompose.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.apiguave.tinderclonecompose.home.NewMatchView
import com.apiguave.tinderclonedata.home.repository.NewMatch

@Composable
fun NewMatchDialog(
    match: NewMatch?,
    onSendMessage: (String) -> Unit,
    onCloseClicked: () -> Unit) {
    Dialog(onDismissRequest = onCloseClicked, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        NewMatchView(match, onSendMessage, onCloseClicked)
    }
}