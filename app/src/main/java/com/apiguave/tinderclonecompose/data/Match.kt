package com.apiguave.tinderclonecompose.data

import android.net.Uri

data class Match(
    val id: String,
    val age: Int,
    val userId: String,
    val name: String,
    val picture: Uri,
    val lastMessage: String?
)
