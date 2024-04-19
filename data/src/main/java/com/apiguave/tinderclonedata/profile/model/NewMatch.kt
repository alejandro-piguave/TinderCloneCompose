package com.apiguave.tinderclonedata.profile.model

data class NewMatch(
    val id: String,
    val userId: String,
    val userName: String,
    val userPictures: List<String>
)
