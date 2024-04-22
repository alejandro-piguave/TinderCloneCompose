package com.apiguave.tinderclonedomain.match

import com.apiguave.tinderclonedomain.profile.Profile

data class Match(
    val id: String,
    val profile: Profile,
    val formattedDate: String,
    val lastMessage: String?
)
