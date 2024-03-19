package com.apiguave.tinderclonecompose.presentation.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.apiguave.tinderclonecompose.data.picture.repository.LocalPicture
import com.apiguave.tinderclonecompose.presentation.components.SelectPictureView

@Composable
fun SelectPictureDialog(onCloseClick: () -> Unit, onReceiveUri: (LocalPicture) -> Unit){
    Dialog(onDismissRequest = onCloseClick, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        SelectPictureView(onCloseClick, onReceiveUri)
    }
}