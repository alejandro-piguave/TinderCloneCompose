package com.apiguave.core_ui.model

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
sealed class ProfilePictureState {
    data class Loading(val name: String): ProfilePictureState()
    data class Remote(val uri: Uri): ProfilePictureState()
}