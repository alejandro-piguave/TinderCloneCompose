package com.apiguave.tinderclonecompose.model

import android.net.Uri

sealed class PictureState {
    data class Loading(val name: String): PictureState()
    data class Remote(val name: String, val uri: Uri): PictureState()
    data class Local(val uri: Uri): PictureState()
}