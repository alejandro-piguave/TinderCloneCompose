package com.apiguave.tinderclonecompose.domain.match.entity

import android.net.Uri

data class Match(
    val id: String,
    val userAge: Int,
    val userId: String,
    val userName: String,
    val userPicture: Uri,
    val formattedDate: String,
    val lastMessage: String?
)
