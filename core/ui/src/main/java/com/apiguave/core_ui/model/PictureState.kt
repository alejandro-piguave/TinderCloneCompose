package com.apiguave.core_ui.model

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
sealed class PictureState {
    data class Loading(val name: String): PictureState()
    data class Remote(val name: String, val uri: Uri): PictureState()
    data class Local(val uri: Uri): PictureState()
}