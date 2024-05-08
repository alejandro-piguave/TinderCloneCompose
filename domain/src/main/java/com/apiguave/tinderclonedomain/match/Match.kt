package com.apiguave.tinderclonedomain.match

import com.apiguave.tinderclonedomain.profile.Profile
import java.time.LocalDate

data class Match(
    val id: String,
    val profile: Profile,
    val creationDate: LocalDate,
    val lastMessage: String?
)
