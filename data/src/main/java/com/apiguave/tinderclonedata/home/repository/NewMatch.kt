package com.apiguave.tinderclonedata.home.repository

data class NewMatch(
    val id: String,
    val userId: String,
    val userName: String,
    val userPictures: List<String>
)
