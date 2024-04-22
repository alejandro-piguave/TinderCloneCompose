package com.apiguave.tinderclonedomain.match

import com.apiguave.tinderclonedomain.profile.RemotePicture

data class Match(
    val id: String,
    val userAge: Int,
    val userId: String,
    val userName: String,
    val userPictures: List<RemotePicture>,
    val formattedDate: String,
    val lastMessage: String?
)
