package com.apiguave.core_ui.components.dialogs

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.apiguave.core_ui.components.SelectPictureView

@Composable
fun SelectPictureDialog(onCloseClick: () -> Unit, onReceiveUri: (Uri) -> Unit){
    Dialog(onDismissRequest = onCloseClick, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        SelectPictureView(onCloseClick, onReceiveUri)
    }
}