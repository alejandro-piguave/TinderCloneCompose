package com.apiguave.tinderclonecompose.model

import android.net.Uri

sealed class ProfilePictureState {
    data class Loading(val name: String): ProfilePictureState()
    data class Remote(val uri: Uri): ProfilePictureState()
}