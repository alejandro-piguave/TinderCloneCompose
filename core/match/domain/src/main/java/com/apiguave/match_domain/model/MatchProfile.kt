package com.apiguave.match_domain.model

data class MatchProfile(
    val id: String,
    val name: String,
    val age: Int,
    val pictureNames: List<String>
)