package com.apiguave.tinderclonecompose.domain.home.entity

data class NewMatch(
    val id: String,
    val userId: String,
    val userName: String,
    val userPictures: List<String>
)
