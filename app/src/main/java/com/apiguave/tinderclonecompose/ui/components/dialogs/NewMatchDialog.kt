package com.apiguave.tinderclonecompose.ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.apiguave.tinderclonecompose.data.home.entity.NewMatch
import com.apiguave.tinderclonecompose.ui.home.NewMatchView

@Composable
fun NewMatchDialog(
    match: NewMatch?,
    onSendMessage: (String) -> Unit,
    onCloseClicked: () -> Unit) {
    Dialog(onDismissRequest = onCloseClicked, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        NewMatchView(match, onSendMessage, onCloseClicked)
    }
}