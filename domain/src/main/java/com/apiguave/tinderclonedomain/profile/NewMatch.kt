package com.apiguave.tinderclonedomain.profile

data class NewMatch(
    val id: String,
    val userId: String,
    val userName: String,
    val userPictures: List<String>
)
