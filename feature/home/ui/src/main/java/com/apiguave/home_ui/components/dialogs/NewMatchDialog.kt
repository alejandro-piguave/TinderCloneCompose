package com.apiguave.home_ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.apiguave.core_ui.model.ProfilePictureState
import com.apiguave.home_ui.components.NewMatchView

@Composable
fun NewMatchDialog(
    pictureStates: List<ProfilePictureState>,
    onSendMessage: (String) -> Unit,
    onCloseClicked: () -> Unit) {
    Dialog(onDismissRequest = onCloseClicked, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        NewMatchView(pictureStates, onSendMessage, onCloseClicked)
    }
}