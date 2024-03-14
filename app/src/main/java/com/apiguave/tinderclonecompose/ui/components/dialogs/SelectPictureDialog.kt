package com.apiguave.tinderclonecompose.ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.apiguave.tinderclonecompose.data.picture.repository.DevicePicture
import com.apiguave.tinderclonecompose.ui.components.SelectPictureView

@Composable
fun SelectPictureDialog(onCloseClick: () -> Unit, onReceiveUri: (DevicePicture) -> Unit){
    Dialog(onDismissRequest = onCloseClick, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        SelectPictureView(onCloseClick, onReceiveUri)
    }
}