package com.apiguave.tinderclonedata.match.repository

data class Match(
    val id: String,
    val userAge: Int,
    val userId: String,
    val userName: String,
    val userPicture: String,
    val formattedDate: String,
    val lastMessage: String?
)
