package com.apiguave.domain_match.model

data class MatchProfile(
    val id: String,
    val name: String,
    val age: Int,
    val pictureNames: List<String>
)