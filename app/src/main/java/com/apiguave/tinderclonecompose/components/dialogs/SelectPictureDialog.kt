package com.apiguave.tinderclonecompose.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.apiguave.tinderclonecompose.components.SelectPictureView
import com.apiguave.tinderclonedata.picture.LocalPicture

@Composable
fun SelectPictureDialog(onCloseClick: () -> Unit, onReceiveUri: (LocalPicture) -> Unit){
    Dialog(onDismissRequest = onCloseClick, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        SelectPictureView(onCloseClick, onReceiveUri)
    }
}