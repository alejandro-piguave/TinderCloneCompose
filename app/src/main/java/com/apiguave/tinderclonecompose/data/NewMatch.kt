package com.apiguave.tinderclonecompose.data

import android.net.Uri

data class NewMatch(
    val id: String,
    val userId: String,
    val userName: String,
    val userPictures: List<Uri>
)
