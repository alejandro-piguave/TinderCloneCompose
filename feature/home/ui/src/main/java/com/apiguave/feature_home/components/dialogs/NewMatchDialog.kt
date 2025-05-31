package com.apiguave.feature_home.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.apiguave.core_ui.model.ProfilePictureState
import com.apiguave.feature_home.components.NewMatchView

@Composable
fun NewMatchDialog(
    pictureStates: List<ProfilePictureState>,
    onSendMessage: (String) -> Unit,
    onCloseClicked: () -> Unit) {
    Dialog(onDismissRequest = onCloseClicked, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        NewMatchView(pictureStates, onSendMessage, onCloseClicked)
    }
}